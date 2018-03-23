package com.dongfg.project.api.web.controller

import com.dongfg.project.api.common.Constants
import com.dongfg.project.api.service.JobService
import com.dongfg.project.api.web.payload.GenericPayload
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * @author dongfg
 * @date 2018/3/23
 */
@RequestMapping("/job")
@RestController
class JobController {

    @Autowired
    private lateinit var jobService: JobService

    @GetMapping
    fun jobs(): List<String> {
        return jobService.jobs()
    }

    @GetMapping("/state")
    fun jobState(@RequestHeader("totp-auth-token") totpCode: String, name: String): GenericPayload {
        return jobService.jobState(totpCode, name)
    }

    @PostMapping("/pause")
    fun jobPause(@RequestHeader("totp-auth-token") totpCode: String, name: String): GenericPayload {
        return jobService.jobOperate(totpCode, name, Constants.JobAction.PAUSE)
    }

    @PostMapping("/resume")
    fun jobResume(@RequestHeader("totp-auth-token") totpCode: String, name: String): GenericPayload {
        return jobService.jobOperate(totpCode, name, Constants.JobAction.RESUME)
    }

    @PostMapping("/remove")
    fun jobRemove(@RequestHeader("totp-auth-token") totpCode: String, name: String): GenericPayload {
        return jobService.jobOperate(totpCode, name, Constants.JobAction.REMOVE)
    }

    @PostMapping("/trigger")
    fun jobTrigger(@RequestHeader("totp-auth-token") totpCode: String, name: String): GenericPayload {
        return jobService.jobOperate(totpCode, name, Constants.JobAction.TRIGGER)
    }
}