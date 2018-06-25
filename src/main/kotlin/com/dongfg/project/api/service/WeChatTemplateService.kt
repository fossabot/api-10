package com.dongfg.project.api.service

import com.dongfg.project.api.common.Constants
import com.dongfg.project.api.common.WeChatTemplate
import com.dongfg.project.api.common.WeChatTemplate.ScheduleReminder
import com.dongfg.project.api.common.WeChatTemplate.ScheduleReminder.ScheduleReminderData
import com.dongfg.project.api.common.WeChatTemplate.TaskNotify.TaskNotifyData
import com.dongfg.project.api.component.WeChat
import com.dongfg.project.api.web.payload.GenericPayload
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

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

        val formIdResponse = getFormId(openId)
        val formId = formIdResponse.data
        if (formId == null) {
            payload.success = false
            payload.msg = formIdResponse.msg
            return payload
        }

        val message = ScheduleReminder(touser = openId, formId = formId, data = data)
        return sendMessage(openId, message)
    }

    fun taskNotify(openId: String, data: TaskNotifyData): GenericPayload {
        val payload = GenericPayload(true)

        val formIdResponse = getFormId(openId)
        val formId = formIdResponse.data
        if (formId == null) {
            payload.success = false
            payload.msg = formIdResponse.msg
            return payload
        }
        val message = WeChatTemplate.TaskNotify(touser = openId, formId = formId, data = data)
        return sendMessage(openId, message)
    }

    private fun sendMessage(openId: String, message: Any): GenericPayload {
        val payload = GenericPayload(true)

        val formIdResponse = getFormId(openId)
        if (!formIdResponse.success) {
            payload.success = false
            payload.msg = formIdResponse.msg
            return payload
        }

        var accessToken = stringRedisTemplate.opsForValue().get(Constants.RedisKey.WECHAT_ACCESS_TOKEN)

        if (accessToken == null) {
            val accessTokenResponse = weChat.accessToken()
            accessToken = accessTokenResponse.accessToken
            if (accessToken == null) {
                payload.success = false
                payload.code = accessTokenResponse.errCode
                payload.msg = accessTokenResponse.errMsg
                return payload
            }

            stringRedisTemplate.opsForValue().set(Constants.RedisKey.WECHAT_ACCESS_TOKEN, accessToken, accessTokenResponse.expiresIn!!.toLong(), TimeUnit.SECONDS)
        }

        val wechatResponse = weChat.message(accessToken, objectMapper.writeValueAsString(message))

        payload.code = wechatResponse.errCode
        payload.msg = wechatResponse.errMsg
        payload.success = wechatResponse.errCode == Constants.PayloadCode.SUCCESS.code
        return payload
    }

    private fun getFormId(openId: String): GenericPayload {
        val formId = stringRedisTemplate.opsForList().rightPop(Constants.RedisKey.WECHAT_FORM_ID + openId)
        val payload = GenericPayload(true)
        if (formId.isNullOrEmpty()) {
            payload.success = false
            payload.msg = "formId not found"
        } else {
            payload.data = formId
        }

        return payload
    }
}