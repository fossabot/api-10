package com.dongfg.project.api.service

import com.dongfg.project.api.common.Constants
import com.dongfg.project.api.common.WeChatTemplate.ScheduleReminder
import com.dongfg.project.api.common.WeChatTemplate.ScheduleReminder.ScheduleReminderData
import com.dongfg.project.api.common.util.WeChatUserHolder
import com.dongfg.project.api.component.WeChat
import com.dongfg.project.api.web.payload.GenericPayload
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class WeChatTemplateService {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var stringRedisTemplate: StringRedisTemplate

    @Autowired
    private lateinit var weChat: WeChat

    fun scheduleReminder(openId: String, data: ScheduleReminderData): GenericPayload {
        val payload = GenericPayload(true)

        val formIdResponse = getFormId()
        if (!formIdResponse.success) {
            payload.success = false
            payload.msg = formIdResponse.msg
            return payload
        }

        val message = ScheduleReminder(touser = openId, formId = formIdResponse.data!!, data = data)

        var accessToken = stringRedisTemplate.opsForValue().get(Constants.RedisKey.WECHAT_ACCESS_TOKEN)

        accessToken?.let {
            val accessTokenResponse = weChat.accessToken()
            if (accessTokenResponse.errCode != Constants.PayloadCode.SUCCESS.code) {
                payload.success = false
                payload.code = accessTokenResponse.errCode
                payload.msg = accessTokenResponse.errMsg
                return payload
            }
            accessToken = accessTokenResponse.accessToken
            stringRedisTemplate.opsForValue().set(Constants.RedisKey.WECHAT_ACCESS_TOKEN, accessToken!!)
        }

        val wechatResponse = weChat.message(accessToken!!, objectMapper.writeValueAsString(message))

        payload.code = wechatResponse.errCode
        payload.msg = wechatResponse.errMsg
        payload.success = wechatResponse.errCode == Constants.PayloadCode.SUCCESS.code
        return payload
    }

    fun addFormId(formId: String): GenericPayload {
        val openId = WeChatUserHolder.getCurrent().openId
        stringRedisTemplate.opsForList().leftPush(Constants.RedisKey.WECHAT_FORM_ID + openId, formId)
        return GenericPayload(true)
    }

    fun getFormId(): GenericPayload {
        val openId = WeChatUserHolder.getCurrent().openId
        val formId = stringRedisTemplate.opsForList().rightPop(Constants.RedisKey.WECHAT_FORM_ID + openId)
        val payload = GenericPayload(true)
        if (formId.isNullOrEmpty()) {
            payload.success = false
            payload.msg = "formId not found"
        }

        return payload
    }

    fun countFormId(openId: String? = WeChatUserHolder.getCurrent().openId): Int {
        val list = stringRedisTemplate.opsForList().range(Constants.RedisKey.WECHAT_FORM_ID + openId, 0, -1)
        var count = 0
        list?.let {
            count = it.size
        }
        return count
    }
}