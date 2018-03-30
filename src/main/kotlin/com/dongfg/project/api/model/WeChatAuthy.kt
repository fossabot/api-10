package com.dongfg.project.api.model

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import javax.persistence.Id

/**
 * @author dongfg
 * @date 2018/3/29
 */
@Document(collection = "wechat_authy")
data class WeChatAuthy(
        @field: Id var id: String? = null,
        var openId: String,
        var site: String? = null,
        var account: String? = null,
        var secret: String,
        var createTime: LocalDateTime,
        var updateTime: LocalDateTime
)