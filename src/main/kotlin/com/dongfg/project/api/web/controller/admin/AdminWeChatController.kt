package com.dongfg.project.api.web.controller.admin

import com.dongfg.project.api.model.WeChatUser
import com.dongfg.project.api.service.WeChatService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/admin/wechat")
@RestController
class AdminWeChatController {

    @Autowired
    private lateinit var weChatService: WeChatService

    @RequestMapping("users")
    fun users(@RequestParam page: Int, @RequestParam size: Int): Page<WeChatUser> {
        return weChatService.listUser(PageRequest.of(page, size))
    }
}