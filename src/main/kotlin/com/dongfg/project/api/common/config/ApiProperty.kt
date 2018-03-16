package com.dongfg.project.api.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * @author dongfg
 * @date 2018/3/16
 */
@Configuration
@ConfigurationProperties(prefix = "api")
class ApiProperty {
    lateinit var yunpianApikey: String
}