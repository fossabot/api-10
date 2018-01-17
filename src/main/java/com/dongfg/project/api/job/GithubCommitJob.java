package com.dongfg.project.api.job;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dongfg.project.api.component.quartz.builder.CronJob;
import com.dongfg.project.api.config.property.ApiProperty;
import com.dongfg.project.api.graphql.type.Message;
import com.dongfg.project.api.graphql.type.MessageLevel;
import com.dongfg.project.api.graphql.type.MessageType;
import com.dongfg.project.api.service.MessageService;
import com.dongfg.project.api.util.Constants;
import com.dongfg.project.api.util.DateTimeConverter;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author dongfg
 * @date 18-1-3
 */
@Component
@Profile("prd")
public class GithubCommitJob extends BaseScheduleJob implements Job {

    private static final String GITHUB_API = "https://api.github.com";

    private static final String KEY_GITHUB_USER = "GITHUB_USER";

    @Autowired
    private ApiProperty apiProperty;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MessageService messageService;

    @Override
    CronJob buildJob() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(KEY_GITHUB_USER, "dongfg");
        return CronJob.builder()
                .group("Github")
                .name(GithubCommitJob.class.getSimpleName())
                .jobClass(getClass())
                .jobDataMap(jobDataMap)
                .cronExpression("0 0 20 * * ?")
                .startTime(new Date())
                .build();
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {
            String username = context.getMergedJobDataMap().getString(KEY_GITHUB_USER);
            JSONArray events = restTemplate.getForObject(GITHUB_API + "/users/{username}/events/public"
                    , JSONArray.class, username);
            events.stream().filter(event -> "PushEvent".equals(((JSONObject) event).getString("type")))
                    .findFirst().ifPresent(event -> {
                String repo = ((JSONObject) event).getJSONObject("repo").getString("name");
                JSONArray commits = restTemplate.getForObject(GITHUB_API + "/repos/{repo}/commits"
                        , JSONArray.class, repo);
                commits.stream().findFirst().ifPresent(lastCommit -> {
                    String commitTime = ((JSONObject) lastCommit).getJSONObject("commit").getJSONObject("author").getString
                            ("date");
                    LocalDateTime commitDate = LocalDateTime.parse(commitTime
                            , DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ISO_8601_FORMAT));
                    String commitMessage = ((JSONObject) lastCommit).getJSONObject("commit").getString("message");
                    if (!DateTimeConverter.currentWeek(commitDate)) {
                        HashMap<String, String> variables = new HashMap<>();
                        variables.put("status", "未完成");
                        variables.put("repo", repo);
                        variables.put("commit", commitMessage);
                        Message message = Message.builder()
                                .receiver(apiProperty.getAppPushAccount())
                                .type(MessageType.APP)
                                .title("GitHub每周提交任务")
                                .content(expandMessage(variables))
                                .catalog(Constants.MessageCatalog.GITHUB)
                                .level(MessageLevel.INFO)
                                .time(DateTimeConverter.formatDate(new Date()))
                                .build();

                        messageService.sendMessage(message);
                    }
                });
            });
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }

    @Override
    List<String> messageTemplate() {
        ArrayList<String> template = new ArrayList<>();
        template.add("<p>当前状态：${status}</p>");
        template.add("<p>最后一次提交</p>");
        template.add("<p>仓库：${repo}</p>");
        template.add("<p>提交日志：${commit}</p>");
        return template;
    }
}
