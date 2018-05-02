package com.dongfg.project.api.web.graphql.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.dongfg.project.api.model.Message
import com.dongfg.project.api.service.MessageService
import com.dongfg.project.api.web.payload.GenericPayload
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MessageResolver : GraphQLMutationResolver {
    @Autowired
    private lateinit var messageService: MessageService

    fun messageSend(totpCode: String, message: Message): GenericPayload {
        return messageService.sendMessage(totpCode, message)
    }
}