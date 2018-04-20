package com.dongfg.project.api.web.controller

import com.dongfg.project.api.model.WeChatAuthy
import com.dongfg.project.api.service.WeChatService
import com.dongfg.project.api.web.payload.GenericPayload
import com.dongfg.project.api.web.payload.WeChatLoginPayload
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * filter by WeChatFilter
 *
 * @author dongfg
 * @date 2018/3/28
 * @see com.dongfg.project.api.web.filter.WeChatFilter
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

    @GetMapping("/authy")
    fun listAuthy(): List<WeChatAuthy> {
        return weChatService.listAuthy()
    }

    @PostMapping("/authy")
    fun saveAuthy(@RequestBody authy: WeChatAuthy): GenericPayload {
        return weChatService.saveOrUpdateAuthy(authy)
    }

    @DeleteMapping("/authy")
    fun deleteAuthy(id: String): GenericPayload {
        return weChatService.deleteAuthy(id)
    }
}