package com.dongfg.project.api.common.config

import com.dongfg.project.api.common.property.ApiProperty
import com.dongfg.project.api.service.WeChatService
import com.dongfg.project.api.web.filter.WeChatFilter
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * @author dongfg
 * @date 2018/3/17
 */
@Configuration
class WebMvcConfig : WebMvcConfigurer {

    @Autowired
    private lateinit var weChatService: WeChatService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var apiProperty: ApiProperty

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addRedirectViewController("/rest", "/swagger-ui.html")
        registry.addRedirectViewController("/", "/graphiql")
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins(apiProperty.admin.url)
    }

    @Bean
    fun filterRegistrationBean(): FilterRegistrationBean<WeChatFilter> {
        val registrationBean = FilterRegistrationBean<WeChatFilter>()
        registrationBean.filter = WeChatFilter(weChatService, objectMapper)
        registrationBean.addUrlPatterns("/wechat/authy")
        return registrationBean
    }
}