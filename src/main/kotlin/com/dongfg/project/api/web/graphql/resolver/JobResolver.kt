package com.dongfg.project.api.web.graphql.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.dongfg.project.api.common.Constants.JobAction.*
import com.dongfg.project.api.service.JobService
import com.dongfg.project.api.web.payload.GenericPayload
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * @author dongfg
 * @date 2018/3/23
 */
@Component
class JobResolver : GraphQLQueryResolver, GraphQLMutationResolver {
    companion object : KLogging()

    @Autowired
    private lateinit var jobService: JobService

    fun jobs(): List<String> {
        return jobService.jobs()
    }

    fun jobState(totpCode: String, name: String): GenericPayload {
        return jobService.jobState(totpCode, name)
    }

    fun jobPause(totpCode: String, name: String): GenericPayload {
        return jobService.jobOperate(totpCode, name, PAUSE)
    }

    fun jobResume(totpCode: String, name: String): GenericPayload {
        return jobService.jobOperate(totpCode, name, RESUME)
    }

    fun jobRemove(totpCode: String, name: String): GenericPayload {
        return jobService.jobOperate(totpCode, name, REMOVE)
    }

    fun jobTrigger(totpCode: String, name: String): GenericPayload {
        return jobService.jobOperate(totpCode, name, TRIGGER)
    }
}