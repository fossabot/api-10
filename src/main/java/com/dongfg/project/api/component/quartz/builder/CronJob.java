package com.dongfg.project.api.component.quartz.builder;

import lombok.Builder;
import lombok.Data;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.TriggerListener;
import org.quartz.listeners.JobListenerSupport;
import org.quartz.listeners.SchedulerListenerSupport;

import java.util.Date;

/**
 * @author dongfg
 * @date 17-12-21
 */
@Data
@Builder
public class CronJob {
    private String group;
    private String name;

    private Class<? extends Job> jobClass;
    private JobDataMap jobDataMap;

    private String cronExpression;
    private Date startTime;
    private Date endTime;

    private JobListenerSupport jobListener;
    private SchedulerListenerSupport schedulerListener;
    private TriggerListener triggerListener;
}
