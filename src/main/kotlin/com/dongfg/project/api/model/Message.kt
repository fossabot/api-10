package com.dongfg.project.api.model

import com.dongfg.project.api.common.Constants
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import javax.persistence.Id

/**
 * @author dongfg
 * @date 2018/3/22
 */
@Document(collection = "message")
data class Message(
        @field: [Id JsonIgnore] var id: String? = null,
        var type: Constants.MessageType,
        var level: Constants.MessageLevel,
        var receiver: String,
        var title: String,
        var content: String,
        var catalog: String,
        var time: LocalDateTime? = LocalDateTime.now()
)