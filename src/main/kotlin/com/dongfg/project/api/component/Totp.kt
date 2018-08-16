package com.dongfg.project.api.component

import com.dongfg.project.api.common.Constants
import com.dongfg.project.api.common.property.ApiProperty
import com.dongfg.project.api.common.util.RamRateLimiter
import com.warrenstrange.googleauth.GoogleAuthenticator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class Totp {

    companion object {
        private var secret: String? = null

        @JvmStatic
        fun validate(totpCode: String): Boolean {
            return GoogleAuthenticator().authorize(secret, totpCode.toInt())
        }

        @JvmStatic
        fun <T> validate(totpCode: String, callback: (String) -> T): T? {
            return when {
                !RamRateLimiter.acquire(Constants.RateLimitKey.MESSAGE) -> callback("rate limit exceeded")
                !validate(totpCode) -> callback("invalid totp")
                else -> null
            }
        }
    }

    @Autowired
    private lateinit var apiProperty: ApiProperty

    @PostConstruct
    fun init() {
        secret = apiProperty.totp.secret
    }

}