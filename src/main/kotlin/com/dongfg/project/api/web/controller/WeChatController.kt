package com.dongfg.project.api.web.controller

import com.dongfg.project.api.model.WeChatAuthy
import com.dongfg.project.api.service.WeChatService
import com.dongfg.project.api.service.WeChatTemplateService
import com.dongfg.project.api.web.payload.GenericPayload
import com.dongfg.project.api.web.payload.WeChatLoginPayload
import mu.KLogging
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.security.Security


@RequestMapping("/wechat")
@RestController
class WeChatController {
    companion object : KLogging() {
        init {
            Security.addProvider(BouncyCastleProvider())
        }
    }

    @Autowired
    private lateinit var weChatService: WeChatService

    @Autowired
    private lateinit var weChatTemplateService: WeChatTemplateService

    @PostMapping("/login")
    fun login(code: String): WeChatLoginPayload {
        return weChatService.login(code)
    }

    @PostMapping("/userInfo", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun saveUserInfo(encryptedData: String, iv: String): GenericPayload {
        return weChatService.decryptUserInfo(encryptedData, iv)
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

    @PostMapping("/formId/{formId}")
    fun addFormId(@PathVariable formId: String): GenericPayload {
        return weChatTemplateService.addFormId(formId)
    }

    @GetMapping("/formId/count")
    fun countFormId(): Int {
        return weChatTemplateService.countFormId()
    }

    @GetMapping("/feeds")
    fun listFeed() {

    }
}