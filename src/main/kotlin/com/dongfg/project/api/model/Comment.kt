package com.dongfg.project.api.model

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import javax.persistence.Id

@ApiModel
@Document(collection = "comment")
data class Comment(
        @field: [Id JsonIgnore] var id: String? = null,
        @ApiModelProperty("your comment")
        var comment: String,
        @ApiModelProperty("your nickname")
        var name: String,
        @ApiModelProperty("your email")
        var email: String? = null,
        @field: JsonIgnore var clientIp: String? = null,
        @ApiModelProperty(hidden = true)
        var createTime: LocalDateTime? = LocalDateTime.now()
)