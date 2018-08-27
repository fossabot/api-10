package com.dongfg.project.api.model

import com.dongfg.project.api.common.Constants
import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import javax.persistence.Id

@ApiModel
@Document(collection = "message")
data class Message(
    @field: [Id JsonIgnore] var id: String? = null,
    @ApiModelProperty("message type")
    var type: Constants.MessageType,
    @ApiModelProperty("message level")
    var level: Constants.MessageLevel,
    @ApiModelProperty("email address,mobile and so on")
    var receiver: String,
    @ApiModelProperty("message title")
    var title: String,
    @ApiModelProperty("message content")
    var content: String,
    @ApiModelProperty("message catalog")
    var catalog: String,
    @ApiModelProperty(hidden = true)
    var time: LocalDateTime? = LocalDateTime.now()
)