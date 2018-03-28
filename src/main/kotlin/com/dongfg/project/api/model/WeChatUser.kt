package com.dongfg.project.api.model

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import javax.persistence.Id

/**
 * @author dongfg
 * @date 2018/3/28
 */
@Document(collection = "wechat_user")
data class WeChatUser(
        @field: Id var openId: String,
        var sessionKey: String,
        var token: String,
        var updateTime: LocalDateTime
)