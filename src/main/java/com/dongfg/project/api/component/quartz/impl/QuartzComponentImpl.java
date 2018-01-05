package com.dongfg.project.api.component.quartz.impl;

import com.dongfg.project.api.component.quartz.QuartzComponent;
import com.dongfg.project.api.component.quartz.builder.CronJob;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.KeyMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * quartz wrapper
 *
 * @author dongfg
 * @date 17-12-21
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class QuartzComponentImpl implements QuartzComponent {

    @NonNull
    private Scheduler scheduler;

    @Override
    public void submitJob(CronJob cronJob) {

        JobKey jobKey = JobKey.jobKey(cronJob.getName(), cronJob.getGroup());
        TriggerKey triggerKey = TriggerKey.triggerKey(cronJob.getName(), cronJob.getGroup());

        JobDetail job = JobBuilder.newJob(cronJob.getJobClass())
                .withIdentity(jobKey)
                .setJobData(cronJob.getJobDataMap())
                .requestRecovery().build();

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .startAt(cronJob.getStartTime())
                .endAt(cronJob.getEndTime())
                .withSchedule(CronScheduleBuilder.cronSchedule(cronJob.getCronExpression())).build();

        try {
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            log.error("Quartz#submitJob", e);
        }

        try {
            ListenerManager listenerManager = scheduler.getListenerManager();
            if (cronJob.getSchedulerListener() != null) {
                listenerManager.addSchedulerListener(cronJob.getSchedulerListener());
            }

            if (cronJob.getJobListener() != null) {
                listenerManager.addJobListener(cronJob.getJobListener(), KeyMatcher.keyEquals(jobKey));
            }

            if (cronJob.getTriggerListener() != null) {
                listenerManager.addTriggerListener(cronJob.getTriggerListener(), KeyMatcher.keyEquals(triggerKey));
            }

        } catch (SchedulerException ignore) {
        }
    }

    @Override
    public List<JobKey> removeJob(@Nullable String group, @Nullable String name) {
        List<JobKey> removeList = new ArrayList<>();
        List<JobKey> jobKeyList = getJobKeys(group, name);
        if (jobKeyList.isEmpty()) {
            return jobKeyList;
        }
        jobKeyList.forEach(jobKey -> {
            try {
                scheduler.deleteJob(jobKey);
                removeList.add(jobKey);
            } catch (SchedulerException e) {
                log.error("Quartz#removeJob", e);
            }
        });
        return removeList;
    }

    @Override
    public List<JobKey> pauseJob(@Nullable String group, @Nullable String name) {
        List<JobKey> pauseList = new ArrayList<>();

        List<JobKey> jobKeyList = getJobKeys(group, name);
        if (jobKeyList.isEmpty()) {
            return jobKeyList;
        }

        if (StringUtils.isEmpty(group) && StringUtils.isEmpty(name)) {
            try {
                scheduler.pauseAll();
                return jobKeyList;
            } catch (SchedulerException e) {
                log.error("Quartz#pauseAll", e);
            }
        } else {
            jobKeyList.forEach(jobKey -> {
                try {
                    scheduler.pauseJob(jobKey);
                    pauseList.add(jobKey);
                } catch (SchedulerException e) {
                    log.error("Quartz#pauseJob", e);
                }
            });
        }

        return pauseList;
    }

    @Override
    public List<JobKey> resumeJob(@Nullable String group, @Nullable String name) {
        List<JobKey> pauseList = new ArrayList<>();

        List<JobKey> jobKeyList = getJobKeys(group, name);
        if (jobKeyList.isEmpty()) {
            return jobKeyList;
        }

        if (StringUtils.isEmpty(group) && StringUtils.isEmpty(name)) {
            try {
                scheduler.resumeAll();
                return jobKeyList;
            } catch (SchedulerException e) {
                log.error("Quartz#resumeAll", e);
            }
        } else {
            jobKeyList.forEach(jobKey -> {
                try {
                    scheduler.resumeJob(jobKey);
                    pauseList.add(jobKey);
                } catch (SchedulerException e) {
                    log.error("Quartz#resumeJob", e);
                }
            });
        }

        return pauseList;
    }

    /**
     * 查询任务列表
     *
     * @param group 组名
     * @param name  任务名
     * @return jobKey list
     */
    @Override
    public List<JobKey> getJobKeys(String group, String name) {
        List<JobKey> jobKeyList = new ArrayList<>();

        try {
            List<String> jobGroupNames = scheduler.getJobGroupNames();
            if (StringUtils.isEmpty(group) && StringUtils.isEmpty(name)) {
                for (String jobGroupName : jobGroupNames) {
                    jobKeyList.addAll(scheduler.getJobKeys(GroupMatcher.groupEquals(jobGroupName)));
                }
            } else if (StringUtils.isEmpty(group) && StringUtils.isNotEmpty(name)) {
                jobKeyList = jobGroupNames.stream().map(groupName -> JobKey.jobKey(name, groupName))
                        .collect(Collectors.toList());
            } else if (StringUtils.isNotEmpty(group) && StringUtils.isEmpty(name)) {
                jobKeyList.addAll(scheduler.getJobKeys(GroupMatcher.groupEquals(group)));
            } else {
                jobKeyList.add(JobKey.jobKey(name, group));
            }
        } catch (SchedulerException ignore) {
        }

        return jobKeyList;
    }

    /**
     * 检查任务是否存在
     *
     * @param group 组名
     * @param name 任务名
     * @return 任务存在true
     */
    @Override
    public boolean checkExists(String group, String name) {
        try {
            return scheduler.checkExists(JobKey.jobKey(name, group));
        } catch (SchedulerException ignore) {
        }
        return false;
    }
}
