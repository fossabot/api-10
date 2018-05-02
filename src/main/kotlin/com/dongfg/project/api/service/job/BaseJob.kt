package com.dongfg.project.api.service.job

import com.dongfg.project.api.component.Quartz
import org.springframework.beans.factory.annotation.Autowired

abstract class BaseJob {
    @Autowired
    protected lateinit var quartz: Quartz

    abstract fun submit()
}