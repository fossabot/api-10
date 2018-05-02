package com.dongfg.project.api.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "wechat_authy")
@Entity
data class WeChatAuthy(
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        var id: String? = null,
        @field: JsonIgnore var openId: String? = null,
        var site: String? = null,
        var account: String? = null,
        var secret: String,
        var createTime: LocalDateTime? = null,
        var updateTime: LocalDateTime = LocalDateTime.now()
)