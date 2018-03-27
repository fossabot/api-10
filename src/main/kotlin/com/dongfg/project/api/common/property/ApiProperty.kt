package com.dongfg.project.api.common.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration


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
     * sms config
     */
    var sms = Sms()

    /**
     * totp config
     */
    var totp = Totp()


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
}