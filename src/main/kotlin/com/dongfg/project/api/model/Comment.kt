package com.dongfg.project.api.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import javax.persistence.Id

/**
 * @author dongfg
 * @date 2018/3/19
 */
@Document
data class Comment(
        @field: [Id JsonIgnore] var id: String? = null,
        var comment: String,
        var name: String,
        var email: String? = null,
        @field: JsonIgnore var clientIp: String? = null,
        var createTime: LocalDateTime? = LocalDateTime.now()
)