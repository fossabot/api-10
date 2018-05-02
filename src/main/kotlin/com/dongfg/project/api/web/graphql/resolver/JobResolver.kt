package com.dongfg.project.api.web.graphql.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.dongfg.project.api.common.Constants
import com.dongfg.project.api.common.util.whenInvalid
import com.dongfg.project.api.component.Totp
import com.dongfg.project.api.service.JobService
import com.dongfg.project.api.web.payload.GenericPayload
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class JobResolver : GraphQLQueryResolver, GraphQLMutationResolver {
    companion object : KLogging()

    @Autowired
    private lateinit var jobService: JobService

    @Autowired
    private lateinit var totp: Totp

    fun jobs(): List<String> {
        return jobService.jobs()
    }

    fun jobState(totpCode: String, name: String): GenericPayload {
        totp.whenInvalid(totpCode) {
            val payload = GenericPayload(false)
            payload.msg = it
            return payload
        }
        return jobService.jobState(name)
    }

    fun jobOperate(totpCode: String, name: String, action: Constants.JobAction): GenericPayload {
        totp.whenInvalid(totpCode) {
            val payload = GenericPayload(false)
            payload.msg = it
            return payload
        }
        return jobService.jobOperate(name, action)
    }
}