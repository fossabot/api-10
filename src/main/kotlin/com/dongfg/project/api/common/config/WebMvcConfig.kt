package com.dongfg.project.api.common.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {
    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addRedirectViewController("/rest", "/swagger-ui.html")
        registry.addRedirectViewController("/", "/graphiql")
    }
}