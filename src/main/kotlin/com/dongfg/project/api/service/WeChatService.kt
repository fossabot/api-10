package com.dongfg.project.api.service

import com.dongfg.project.api.common.Constants
import com.dongfg.project.api.common.util.WeChatUserHolder
import com.dongfg.project.api.component.WeChat
import com.dongfg.project.api.model.WeChatAuthy
import com.dongfg.project.api.model.WeChatUser
import com.dongfg.project.api.model.WeChatUserInfo
import com.dongfg.project.api.repository.WeChatAuthyRepository
import com.dongfg.project.api.repository.WeChatUserInfoRepository
import com.dongfg.project.api.repository.WeChatUserRepository
import com.dongfg.project.api.web.payload.GenericPayload
import com.dongfg.project.api.web.payload.WeChatLoginPayload
import com.fasterxml.jackson.databind.ObjectMapper
import io.undertow.server.session.SecureRandomSessionIdGenerator
import mu.KLogging
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.util.Base64Utils
import java.security.AlgorithmParameters
import java.security.Security
import java.time.LocalDateTime
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


@Service
class WeChatService {
    companion object : KLogging() {
        val tokenGenerator = SecureRandomSessionIdGenerator()

        init {
            Security.addProvider(BouncyCastleProvider())
        }
    }

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var weChat: WeChat

    @Autowired
    private lateinit var weChatUserRepository: WeChatUserRepository

    @Autowired
    private lateinit var weChatUserInfoRepository: WeChatUserInfoRepository

    @Autowired
    private lateinit var weChatAuthyRepository: WeChatAuthyRepository

    @Autowired
    private lateinit var weChatTemplateService: WeChatTemplateService

    fun login(code: String): WeChatLoginPayload {
        val session = weChat.code2session(code)
        return if (session.errCode == Constants.PayloadCode.SUCCESS.code) {
            val token = tokenGenerator.createSessionId()
            val user = WeChatUser(session.openId!!, session.sessionKey!!, token, LocalDateTime.now())
            weChatUserRepository.save(user)
            WeChatLoginPayload(true, token = token)
        } else {
            WeChatLoginPayload(false, msg = session.errMsg)
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
            authy.createTime = LocalDateTime.now()
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

    fun decryptUserInfo(encryptedData: String, iv: String): GenericPayload {
        val keyByte = Base64Utils.decode(WeChatUserHolder.getCurrent().sessionKey.toByteArray())
        val content = Base64Utils.decode(encryptedData.toByteArray())
        val ivByte = Base64Utils.decode(iv.toByteArray())

        val rawUserInfo = decrypt(content, keyByte, ivByte)

        val weChatUserInfo = objectMapper.readValue<WeChatUserInfo>(rawUserInfo, WeChatUserInfo::class.java)
        weChatUserInfoRepository.save(weChatUserInfo)

        return GenericPayload(true)
    }

    fun listUser(page: PageRequest): Page<WeChatUser> {
        return weChatUserRepository.findAll(page).onEach {
            val user = it
            weChatUserInfoRepository.findById(it.openId).ifPresent { user.userInfo = it }
            user.expireTime = user.updateTime.plusSeconds(7200)
            user.pushCount = weChatTemplateService.countFormId(it.openId)
        }
    }

    private fun decrypt(content: ByteArray, keyByte: ByteArray, ivByte: ByteArray): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val keySpec = SecretKeySpec(keyByte, "AES")

        val params = AlgorithmParameters.getInstance("AES")
        params.init(IvParameterSpec(ivByte))

        cipher.init(Cipher.DECRYPT_MODE, keySpec, params)

        return String(cipher.doFinal(content))
    }
}