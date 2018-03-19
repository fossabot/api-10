package com.dongfg.project.api.component

import org.quartz.*
import org.quartz.impl.matchers.GroupMatcher
import org.quartz.impl.matchers.KeyMatcher
import org.quartz.listeners.JobListenerSupport
import org.quartz.listeners.SchedulerListenerSupport
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors


/**
 * @author dongfg
 * @date 2018/3/16
 */
@Component
class Quartz {

    companion object {
        val log = LoggerFactory.getLogger(Quartz::class.java.name)!!
    }

    @Autowired
    private lateinit var scheduler: Scheduler

    /**
     * submit job
     * @throws SchedulerException if submit failed
     */
    @Throws(SchedulerException::class)
    fun submitJob(name: String,
                  jobClass: Class<out Job>,
                  jobDataMap: JobDataMap? = JobDataMap(),
                  cronExpression: String,
                  startTime: Date? = Date(),
                  endTime: Date? = null,
                  jobListener: JobListenerSupport? = null,
                  schedulerListener: SchedulerListenerSupport? = null,
                  triggerListener: TriggerListener? = null) {


        val jobKey = JobKey.jobKey(name)
        val triggerKey = TriggerKey.triggerKey(name)

        val job = JobBuilder.newJob(jobClass)
                .withIdentity(jobKey)
                .setJobData(jobDataMap)
                .requestRecovery().build()

        val trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .startAt(startTime)
                .endAt(endTime)
                .withSchedule(CronScheduleBuilder
                        .cronSchedule(cronExpression)
                ).build()

        scheduler.scheduleJob(job, trigger)

        val listenerManager = scheduler.listenerManager

        whenNotNull(schedulerListener) {
            listenerManager.addSchedulerListener(schedulerListener)
        }

        whenNotNull(jobListener) {
            listenerManager.addJobListener(jobListener, KeyMatcher.keyEquals(jobKey))
        }

        whenNotNull(triggerListener) {
            listenerManager.addTriggerListener(triggerListener, KeyMatcher.keyEquals(triggerKey))
        }
    }

    /**
     * get all job list
     */
    fun getJobKeys(): List<JobKey> {
        return scheduler.getJobKeys(GroupMatcher.anyJobGroup()).stream().collect(Collectors.toList())
    }

    fun getTrigger(name: String): Trigger {
        return scheduler.getTrigger(TriggerKey.triggerKey(name))
    }

    fun getTriggerState(name: String): Trigger.TriggerState {
        return scheduler.getTriggerState(TriggerKey.triggerKey(name))
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
        } catch (e: SchedulerException) {
            log.error("pauseJob failed", e)
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
        } catch (e: SchedulerException) {
            log.error("resumeJob failed", e)
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
        } catch (e: SchedulerException) {
            log.error("triggerJob failed", e)
        }
        return false
    }

}

inline fun <T : Any, R> whenNotNull(input: T?, callback: (T) -> R): R? {
    return input?.let(callback)
}