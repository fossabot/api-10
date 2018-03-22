package com.dongfg.project.api.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Id

/**
 * @author dongfg
 * @date 2018/3/22
 */
@Document
data class Message(
        @field: [Id JsonIgnore] var id: String? = null
)