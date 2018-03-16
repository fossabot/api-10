package com.dongfg.project.api.component.quartz

import org.quartz.Job
import org.quartz.JobDataMap
import org.quartz.TriggerListener
import org.quartz.listeners.JobListenerSupport
import org.quartz.listeners.SchedulerListenerSupport
import java.util.*


/**
 * @author dongfg
 * @date 2018/3/16
 */
data class CronJob(
        var name: String? = null,

        var jobClass: Class<out Job>,
        var jobDataMap: JobDataMap? = JobDataMap(),

        var cronExpression: String,
        var startTime: Date? = null,
        var endTime: Date? = null,

        var jobListener: JobListenerSupport? = null,
        var schedulerListener: SchedulerListenerSupport? = null,
        var triggerListener: TriggerListener? = null
)