package com.dongfg.project.api.service

import com.dongfg.project.api.common.util.whenTotpInvalid
import com.dongfg.project.api.component.Totp
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author dongfg
 * @date 2018/3/22
 */
@Service
class MessageService {
    companion object : KLogging()

    @Autowired
    private lateinit var totp: Totp

    fun sendMessage(totpCode: String) {
        whenTotpInvalid(totp, totpCode) {

        }
    }
}