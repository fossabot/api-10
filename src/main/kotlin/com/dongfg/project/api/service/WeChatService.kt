package com.dongfg.project.api.service

import com.dongfg.project.api.common.util.WeChatUserHolder
import com.dongfg.project.api.component.WeChat
import com.dongfg.project.api.model.WeChatAuthy
import com.dongfg.project.api.model.WeChatUser
import com.dongfg.project.api.repository.WeChatAuthyRepository
import com.dongfg.project.api.repository.WeChatUserRepository
import com.dongfg.project.api.web.payload.GenericPayload
import com.dongfg.project.api.web.payload.WeChatLoginPayload
import io.undertow.server.session.SecureRandomSessionIdGenerator
import mu.KLogging
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

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

    @Autowired
    private lateinit var weChatAuthyRepository: WeChatAuthyRepository

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

    fun listAuthy(): List<WeChatAuthy> {
        return weChatAuthyRepository.findByOpenId(WeChatUserHolder.getCurrent().openId, Sort.by(Sort.Order.desc("createTime")))
    }

    fun saveOrUpdateAuthy(authy: WeChatAuthy): GenericPayload {
        if (!authy.id.isNullOrEmpty()) {
            weChatAuthyRepository.findById(authy.id!!).ifPresent {
                it.site = authy.site
                it.account = authy.account
                it.secret = authy.secret
                weChatAuthyRepository.save(it)
            }
        } else {
            authy.openId = WeChatUserHolder.getCurrent().openId
            weChatAuthyRepository.save(authy)
        }
        return GenericPayload(true, data = authy.id)
    }

    fun deleteAuthy(id: String): GenericPayload {
        weChatAuthyRepository.deleteById(id)
        return GenericPayload(true)
    }

    fun findUser(token: String): Optional<WeChatUser> {
        return weChatUserRepository.findByToken(token)
    }
}