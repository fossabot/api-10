package com.dongfg.project.api.component

import com.dongfg.project.api.common.property.ApiProperty
import com.fasterxml.jackson.annotation.JsonProperty
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

/**
 * @author dongfg
 * @date 2018/3/28
 */
@Component
class WeChat {

    companion object : KLogging()

    @Autowired
    private lateinit var restTemplate: RestTemplate

    @Autowired
    private lateinit var apiProperty: ApiProperty


    fun code2session(code: String): Code2SessionResponse? {
        val url = "https://api.weixin.qq.com/sns/jscode2session?appid={appId}&secret={secret}&js_code={jsCode}&grant_type=authorization_code"
        return try {
            restTemplate.getForObject(url, Code2SessionResponse::class.java, apiProperty.wechat.appid, apiProperty.wechat.secret, code)
        } catch (e: RestClientException) {
            logger.error("code2session RestClientException", e)
            val response = Code2SessionResponse()
            response.errMsg = "network error"
            response
        }
    }
}

class Code2SessionResponse {
    @JsonProperty("openid")
    var openId: String? = null
    @JsonProperty("session_key")
    var sessionKey: String? = null
    @JsonProperty("unionid")
    var unionId: String? = null
    @JsonProperty("errcode")
    var errCode: String? = null
    @JsonProperty("errmsg")
    var errMsg: String? = null
}