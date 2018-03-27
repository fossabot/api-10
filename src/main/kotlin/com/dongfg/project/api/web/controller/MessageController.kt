package com.dongfg.project.api.web.controller

import com.dongfg.project.api.common.util.EnableSwaggerDoc
import com.dongfg.project.api.model.Message
import com.dongfg.project.api.service.MessageService
import com.dongfg.project.api.web.payload.GenericPayload
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * @author dongfg
 * @date 2018/3/23
 */
@EnableSwaggerDoc
@RequestMapping("/message")
@RestController
class MessageController {
    companion object : KLogging()

    @Autowired
    private lateinit var messageService: MessageService

    @PostMapping
    fun sendMessage(@RequestHeader("totp-auth-token") totpCode: String, @RequestBody message: Message): GenericPayload {
        return messageService.sendMessage(totpCode, message)
    }
}