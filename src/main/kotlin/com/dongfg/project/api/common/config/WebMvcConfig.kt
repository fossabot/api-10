package com.dongfg.project.api.common.config

import com.dongfg.project.api.service.WeChatService
import com.dongfg.project.api.web.filter.WeChatFilter
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addRedirectViewController("/rest", "/swagger-ui.html")
        registry.addRedirectViewController("/", "/graphiql")
    }

    @Bean
    fun filterRegistrationBean(): FilterRegistrationBean<WeChatFilter> {
        val registrationBean = FilterRegistrationBean<WeChatFilter>()
        registrationBean.filter = WeChatFilter(weChatService, objectMapper)
        registrationBean.addUrlPatterns("/wechat/authy")
        return registrationBean
    }
}