package com.dongfg.project.api.common.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.convert.DurationUnit
import org.springframework.context.annotation.Configuration
import java.time.Duration
import java.time.temporal.ChronoUnit


@Configuration
@ConfigurationProperties("api")
class ApiProperty {

    lateinit var version: String

    lateinit var url: String

    var admin = Admin()

    var sms = Sms()

    var totp = Totp()

    var push = Push()

    var jwt = Jwt()

    var rss = Rss()

    inner class Admin {

        lateinit var url: String
    }

    inner class Sms {

        lateinit var secret: String
    }

    inner class Totp {

        lateinit var secret: String
    }

    inner class Push {

        lateinit var mobile: String

        lateinit var email: String

        lateinit var openId: String
    }

    inner class Jwt {

        lateinit var secret: String

        @DurationUnit(ChronoUnit.MINUTES)
        var timeout: Duration = Duration.ofHours(2)
    }

    inner class Rss {
        lateinit var user: String
        lateinit var password: String
        lateinit var apiUrl: String
    }
}