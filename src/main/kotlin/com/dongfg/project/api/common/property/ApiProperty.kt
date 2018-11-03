package com.dongfg.project.api.common.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@ConfigurationProperties("api")
class ApiProperty {
    lateinit var version: String

    lateinit var url: String

    var sms = Sms()

    var totp = Totp()

    var rss = Rss()
    
    inner class Sms {
        lateinit var secret: String
    }

    inner class Totp {
        lateinit var secret: String
    }

    inner class Rss {
        lateinit var user: String
        lateinit var password: String
        lateinit var apiUrl: String
    }
}