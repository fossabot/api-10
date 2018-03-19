package com.dongfg.project.api.web.graphql.type

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import javax.persistence.Id

/**
 * @author dongfg
 * @date 2018/3/19
 */
@Document
data class Comment(
        @field: Id var id: String?,
        var comment: String,
        var name: String,
        var email: String?,
        var clientIp: String?,
        var createTime: LocalDateTime? = LocalDateTime.now()
)