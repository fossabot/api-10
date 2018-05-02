package com.dongfg.project.api.component

import com.dongfg.project.api.common.Constants
import com.dongfg.project.api.common.property.ApiProperty
import com.fasterxml.jackson.annotation.JsonProperty
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

@Component
class WeChat {

    companion object : KLogging()

    @Autowired
    private lateinit var restTemplate: RestTemplate

    @Autowired
    private lateinit var apiProperty: ApiProperty

    fun code2session(code: String): Code2SessionResponse {
        val url = "https://api.weixin.qq.com/sns/jscode2session?appid={appId}&secret={secret}&js_code={jsCode}&grant_type=authorization_code"
        return try {
            restTemplate.getForObject(url, Code2SessionResponse::class.java, apiProperty.wechat.appid, apiProperty.wechat.secret, code)!!
        } catch (e: RestClientException) {
            logger.error("code2session RestClientException", e)
            val response = Code2SessionResponse()
            response.errMsg = "network error"
            response.errCode = Constants.PayloadCode.FAILURE.code
            response
        }
    }

    fun accessToken(): AccessToken {
        val url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appId}&secret={secret}"
        return try {
            restTemplate.getForObject(url, AccessToken::class.java, apiProperty.wechat.appid, apiProperty.wechat.secret)!!
        } catch (e: RestClientException) {
            logger.error("access oken RestClientException", e)
            val response = AccessToken()
            response.errMsg = "network error"
            response.errCode = Constants.PayloadCode.FAILURE.code
            response
        }
    }

    fun message(accessToken: String, data: String): WeChatResponse {
        val url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token={accessToken}"
        return try {
            restTemplate.postForObject(url, data, WeChatResponse::class.java)!!
        } catch (e: RestClientException) {
            logger.error("send message RestClientException", e)
            val response = WeChatResponse()
            response.errMsg = "network error"
            response.errCode = Constants.PayloadCode.FAILURE.code
            response
        }
    }
}

open class WeChatResponse {
    @JsonProperty("errcode")
    var errCode: Int = Constants.PayloadCode.SUCCESS.code
    @JsonProperty("errmsg")
    var errMsg: String? = null
}

class Code2SessionResponse : WeChatResponse() {
    @JsonProperty("openid")
    var openId: String? = null
    @JsonProperty("session_key")
    var sessionKey: String? = null
    @JsonProperty("unionid")
    var unionId: String? = null
}

class AccessToken : WeChatResponse() {
    @JsonProperty("access_token")
    var accessToken: String? = null
    @JsonProperty("expires_in")
    var expiresIn: Int? = null
}