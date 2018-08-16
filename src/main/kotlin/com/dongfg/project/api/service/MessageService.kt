package com.dongfg.project.api.service

import com.dongfg.project.api.common.Constants.MessageType.*
import com.dongfg.project.api.component.Sms
import com.dongfg.project.api.model.Message
import com.dongfg.project.api.web.payload.GenericPayload
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MessageService {
    companion object : KLogging()

    @Autowired
    private lateinit var sms: Sms

    fun sendMessage(message: Message): GenericPayload {
        val payload = GenericPayload(true)
        when (message.type) {
            SMS -> {
                val result = sms.send(message.receiver, message.content)
                payload.success = result.isSucc
                payload.msg = result.msg
            }
            WEB -> TODO()
            APP -> TODO()
            EMAIL -> TODO()
        }

        return payload
    }
}