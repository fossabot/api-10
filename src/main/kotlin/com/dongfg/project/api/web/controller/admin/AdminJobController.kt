package com.dongfg.project.api.web.controller.admin

import com.dongfg.project.api.model.JobInfo
import com.dongfg.project.api.service.JobService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/admin/job")
@RestController
class AdminJobController {

    @Autowired
    private lateinit var jobService: JobService

    @GetMapping
    fun list(): List<JobInfo> {
        return jobService.jobsDetails()
    }
}