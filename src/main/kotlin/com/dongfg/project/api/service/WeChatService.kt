package com.dongfg.project.api.service

import com.dongfg.project.api.component.WeChat
import com.dongfg.project.api.model.WeChatUser
import com.dongfg.project.api.repository.WeChatUserRepository
import com.dongfg.project.api.web.payload.WeChatLoginPayload
import io.undertow.server.session.SecureRandomSessionIdGenerator
import mu.KLogging
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * @author dongfg
 * @date 2018/3/28
 */
@Service
class WeChatService {
    companion object : KLogging() {
        val tokenGenerator = SecureRandomSessionIdGenerator()
    }

    @Autowired
    private lateinit var weChat: WeChat

    @Autowired
    private lateinit var weChatUserRepository: WeChatUserRepository

    fun login(code: String): WeChatLoginPayload {
        val session = weChat.code2session(code)
        return if (StringUtils.isNotEmpty(session?.openId)) {
            val token = tokenGenerator.createSessionId()
            val user = WeChatUser(session?.openId!!, session.sessionKey!!, token, LocalDateTime.now())
            weChatUserRepository.save(user)
            WeChatLoginPayload(true, token = token)
        } else {
            WeChatLoginPayload(false, msg = session?.errMsg)
        }
    }

    fun checkToken(token: String): Boolean {
        return weChatUserRepository.findByToken(token).isPresent
    }
}