package com.dongfg.project.api.web.controller

import com.dongfg.project.api.common.Constants
import com.dongfg.project.api.common.util.EnableSwaggerDoc
import com.dongfg.project.api.common.util.whenInvalid
import com.dongfg.project.api.component.Totp
import com.dongfg.project.api.service.JobService
import com.dongfg.project.api.web.payload.GenericPayload
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@EnableSwaggerDoc
@RequestMapping("/job")
@RestController
class JobController {

    @Autowired
    private lateinit var jobService: JobService

    @Autowired
    private lateinit var totp: Totp

    @GetMapping
    fun jobs(): List<String> {
        return jobService.jobs()
    }

    @GetMapping("/state")
    fun jobState(@RequestHeader("x-auth-totp") totpCode: String, name: String): GenericPayload {
        totp.whenInvalid(totpCode) {
            val payload = GenericPayload(false)
            payload.msg = it
            return payload
        }
        return jobService.jobState(name)
    }

    @PostMapping("/operate")
    fun jobPause(@RequestHeader("x-auth-totp") totpCode: String, name: String, action: Constants.JobAction): GenericPayload {
        totp.whenInvalid(totpCode) {
            val payload = GenericPayload(false)
            payload.msg = it
            return payload
        }
        return jobService.jobOperate(name, action)
    }
}