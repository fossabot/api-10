package com.dongfg.project.api.component

import com.dongfg.project.api.common.property.ApiProperty
import com.warrenstrange.googleauth.GoogleAuthenticator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * @author dongfg
 * @date 2018/3/22
 */
@Component
class Totp {

    @Autowired
    private lateinit var apiProperty: ApiProperty

    fun validate(totpCode: String): Boolean {
        return GoogleAuthenticator().authorize(apiProperty.otpSecret, totpCode.toInt())
    }

}