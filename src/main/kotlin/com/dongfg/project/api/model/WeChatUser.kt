package com.dongfg.project.api.model

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Transient

@Table(name = "wechat_user")
@Entity
data class WeChatUser(
        @field: Id var openId: String,
        var sessionKey: String,
        var token: String,
        var updateTime: LocalDateTime,
        @Transient
        var expireTime: LocalDateTime? = null,
        @Transient
        var userInfo: WeChatUserInfo? = null,
        @Transient
        var pushCount: Int? = 0
)