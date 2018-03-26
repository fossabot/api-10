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
     * yunpianApikey
     */
    var yunpianApikey: String? = null
    /**
     * totpSecret
     */
    var totpSecret: String? = null
    /**
     * adminUrl
     */
    var adminUrl: String? = null
    /**
     * privateVapid
     */
    var privateVapid: String? = null
}