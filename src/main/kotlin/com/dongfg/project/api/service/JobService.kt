package com.dongfg.project.api.service

import com.dongfg.project.api.common.Constants.JobAction
import com.dongfg.project.api.common.Constants.JobAction.*
import com.dongfg.project.api.component.Quartz
import com.dongfg.project.api.component.Totp
import com.dongfg.project.api.model.JobInfo
import com.dongfg.project.api.web.payload.GenericPayload
import mu.KLogging
import org.quartz.JobKey
import org.quartz.SchedulerException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

/**
 * @author dongfg
 * @date 2018/3/23
 */
@Service
class JobService {
    companion object : KLogging()

    @Autowired
    private lateinit var totp: Totp

    @Autowired
    private lateinit var quartz: Quartz

    fun jobs(): List<String> {
        return quartz.getJobKeys().stream().map(JobKey::getName).collect(Collectors.toList())
    }

    fun jobsDetails(): List<JobInfo> {
        return quartz.getJobKeys().stream().map {
            val jobInfo = JobInfo(it.name)
            jobInfo.state = quartz.getTriggerState(it.name).name
            jobInfo.prevTime = quartz.getTrigger(it.name).previousFireTime
            jobInfo.nextTime = quartz.getTrigger(it.name).nextFireTime
            jobInfo
        }.collect(Collectors.toList())
    }

    fun jobState(name: String): GenericPayload {
        val payload = GenericPayload(false)
        try {
            payload.success = true
            payload.data = quartz.getTriggerState(name).name
        } catch (e: SchedulerException) {
            payload.msg = e.message
        }

        return payload
    }

    fun jobOperate(name: String, action: JobAction): GenericPayload {
        val payload = GenericPayload(false)
        try {
            payload.success = true
            when (action) {
                PAUSE -> quartz.pauseJob(name)
                RESUME -> quartz.resumeJob(name)
                REMOVE -> quartz.removeJob(name)
                TRIGGER -> quartz.triggerJob(name)
            }
        } catch (e: SchedulerException) {
            payload.msg = e.message
        }
        return payload
    }
}