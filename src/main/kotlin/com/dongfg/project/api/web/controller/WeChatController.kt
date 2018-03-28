package com.dongfg.project.api.web.controller

import com.dongfg.project.api.service.WeChatService
import com.dongfg.project.api.web.payload.WeChatLoginPayload
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author dongfg
 * @date 2018/3/28
 */
@RequestMapping("/wechat")
@RestController
class WeChatController {

    @Autowired
    private lateinit var weChatService: WeChatService

    @PostMapping("/login")
    fun login(code: String): WeChatLoginPayload {
        return weChatService.login(code)
    }
}