package com.dongfg.project.api.common.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * @author dongfg
 * @date 2018/3/17
 */
@Configuration
class WebMvcConfig : WebMvcConfigurer {
    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/rest").setViewName("docs/index.html")
    }
}