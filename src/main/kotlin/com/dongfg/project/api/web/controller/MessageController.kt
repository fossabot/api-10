package com.dongfg.project.api.web.controller

import com.dongfg.project.api.common.util.EnableSwaggerDoc
import com.dongfg.project.api.model.Message
import com.dongfg.project.api.service.MessageService
import com.dongfg.project.api.web.payload.GenericPayload
import io.swagger.annotations.ApiOperation
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@EnableSwaggerDoc
@RequestMapping("/message")
@RestController
class MessageController {
    companion object : KLogging()

    @Autowired
    private lateinit var messageService: MessageService

    @ApiOperation(notes = "required `X-Auth-Totp` header", value = "send message")
    @PostMapping
    fun sendMessage(@RequestBody message: Message): GenericPayload {
        return messageService.sendMessage(message)
    }
}