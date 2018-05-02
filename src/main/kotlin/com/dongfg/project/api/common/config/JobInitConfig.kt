package com.dongfg.project.api.common.config

import com.dongfg.project.api.service.job.BaseJob
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener

@Configuration
class JobInitConfig {
    @Autowired(required = false)
    private lateinit var jobs: List<BaseJob>

    @EventListener(ApplicationReadyEvent::class)
    fun initJob() {
        jobs.forEach(BaseJob::submit)
    }
}