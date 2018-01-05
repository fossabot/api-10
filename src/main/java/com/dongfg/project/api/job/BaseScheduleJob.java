package com.dongfg.project.api.job;

import com.dongfg.project.api.component.quartz.QuartzComponent;
import com.dongfg.project.api.component.quartz.builder.CronJob;
import com.google.common.base.Joiner;
import org.apache.commons.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author dongfg
 * @date 18-1-3
 */
public abstract class BaseScheduleJob {

    @Autowired
    private QuartzComponent quartzComponent;


    /**
     * 创建任务
     *
     * @return 任务详情
     */
    abstract CronJob buildJob();

    /**
     * 消息模板，一行一个 String
     *
     * @return 消息模板
     */
    abstract List<String> messageTemplate();

    String expandMessage(Map<String, String> variables) {
        return new StrSubstitutor(variables).replace(Joiner.on("\n").join(messageTemplate()));
    }

    /**
     * init job
     */
    public void submitJob() {
        CronJob job = buildJob();
        if (!quartzComponent.checkExists(job.getGroup(), job.getName())) {
            quartzComponent.submitJob(job);
        }
    }
}
