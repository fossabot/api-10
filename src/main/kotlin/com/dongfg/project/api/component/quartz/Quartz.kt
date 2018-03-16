package com.dongfg.project.api.component.quartz

import org.quartz.*
import org.quartz.impl.matchers.GroupMatcher
import org.quartz.impl.matchers.KeyMatcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.stream.Collectors


/**
 * @author dongfg
 * @date 2018/3/16
 */
@Component
class Quartz {
    
    @Autowired
    private lateinit var scheduler: Scheduler


    /**
     * submit a [cronJob]
     * @throws SchedulerException if submit failed
     */
    @Throws(SchedulerException::class)
    fun submitJob(cronJob: CronJob) {
        fun <T : Any, R> whenNotNull(input: T?, callback: (T) -> R): R? {
            return input?.let(callback)
        }

        val jobKey = JobKey.jobKey(cronJob.name)
        val triggerKey = TriggerKey.triggerKey(cronJob.name)

        val job = JobBuilder.newJob(cronJob.jobClass)
                .withIdentity(jobKey)
                .setJobData(cronJob.jobDataMap)
                .requestRecovery().build()

        val trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .startAt(cronJob.startTime)
                .endAt(cronJob.endTime)
                .withSchedule(CronScheduleBuilder
                        .cronSchedule(cronJob.cronExpression)
                ).build()

        scheduler.scheduleJob(job, trigger)

        val listenerManager = scheduler.listenerManager

        whenNotNull(cronJob.schedulerListener) {
            listenerManager.addSchedulerListener(cronJob.schedulerListener)
        }

        whenNotNull(cronJob.jobListener) {
            listenerManager.addJobListener(cronJob.jobListener, KeyMatcher.keyEquals(jobKey))
        }

        whenNotNull(cronJob.triggerListener) {
            listenerManager.addTriggerListener(cronJob.triggerListener, KeyMatcher.keyEquals(triggerKey))
        }
    }

    /**
     * get all job list
     */
    fun getJobKeys(): List<JobKey> {
        return scheduler.getJobKeys(GroupMatcher.anyJobGroup()).stream().collect(Collectors.toList())
    }

    /**
     * check job exist by [name]
     */
    fun exists(name: String): Boolean {
        return scheduler.checkExists(JobKey.jobKey(name))
    }

    /**
     * remove job by [name]
     */
    fun removeJob(name: String): Boolean {
        return scheduler.deleteJob(JobKey.jobKey(name))
    }

    /**
     * pause job by [name]
     */
    fun pauseJob(name: String): Boolean {
        try {
            if (exists(name)) {
                scheduler.pauseJob(JobKey.jobKey(name))
                return true
            }
        } catch (ignore: SchedulerException) {
        }
        return false
    }

    /**
     * resume job by [name]
     */
    fun resumeJob(name: String): Boolean {
        try {
            if (exists(name)) {
                scheduler.resumeJob(JobKey.jobKey(name))
                return true
            }
        } catch (ignore: SchedulerException) {
        }
        return false
    }

    /**
     * trigger job by [name]
     */
    fun triggerJob(name: String): Boolean {
        try {
            if (exists(name)) {
                scheduler.resumeJob(JobKey.jobKey(name))
                return true
            }
        } catch (ignore: SchedulerException) {
        }
        return false
    }


}