package com.dongfg.project.api.component

import com.dongfg.project.api.common.property.ApiProperty
import com.dongfg.project.api.common.util.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TinyTinyRss {

    @Autowired
    private lateinit var apiProperty: ApiProperty

    fun login() {
        val requestJson = Json {
            "op" To "login"
            "user" To apiProperty.rss.user
            "password" To apiProperty.rss.password
        }

        println(requestJson)
    }
}