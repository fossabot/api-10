package com.dongfg.project.api.service.job

import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component

@Component
class GithubCommitJob : BaseJob(), Job {
    override fun submit() {
        if (!quartz.exists(this::class.java.simpleName)) {
            quartz.submitJob(this::class.java.simpleName
                    , this::class.java
                    , desc = "检查每周Github仓库提交情况"
                    , cronExpression = "0 0/5 * * * ?")
        }
    }

    override fun execute(context: JobExecutionContext) {

    }
}