package com.dongfg.project.api.common.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.convert.DurationUnit
import org.springframework.context.annotation.Configuration
import java.time.Duration
import java.time.temporal.ChronoUnit


/**
 * @author dongfg
 * @date 2018/3/26
 */
@Configuration
@ConfigurationProperties("api")
class ApiProperty {
    /**
     * api version
     */
    lateinit var version: String
    /**
     * base rest api path
     */
    lateinit var url: String

    /**
     * admin config
     */
    var admin = Admin()

    /**
     * sms config
     */
    var sms = Sms()

    /**
     * totp config
     */
    var totp = Totp()

    /**
     * wechat config
     */
    var wechat = Wechat()

    /**
     * push account
     */
    var push = Push()

    /**
     * jwt config
     */
    var jwt = Jwt()

    inner class Admin {
        /**
         * admin url
         */
        lateinit var url: String
    }

    inner class Sms {
        /**
         * sms api secret
         */
        lateinit var secret: String
    }

    inner class Totp {
        /**
         * totp validate secret
         */
        lateinit var secret: String
    }

    inner class Wechat {
        /**
         * wechat mini app id
         */
        lateinit var appid: String

        /**
         * wechat mini app secret
         */
        lateinit var secret: String
    }

    inner class Push {
        /**
         * push mobile
         */
        lateinit var mobile: String

        /**
         * push email
         */
        lateinit var email: String
    }

    inner class Jwt {
        /**
         * jwt secret
         */
        lateinit var secret: String

        @DurationUnit(ChronoUnit.MINUTES)
        var timeout = Duration.ofHours(2)
    }
}