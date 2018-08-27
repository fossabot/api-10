package com.dongfg.project.api.common.config

import com.dongfg.project.api.common.property.ApiProperty
import com.dongfg.project.api.common.util.EnableSwaggerDoc
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.time.LocalDateTime

@EnableSwagger2
@Configuration
class SwaggerConfig {

    @Autowired
    private lateinit var apiProperty: ApiProperty

    @Bean
    fun apiDocket(): Docket {
        // @formatter:off
        return Docket(DocumentationType.SWAGGER_2)
                .host(apiProperty.url)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(EnableSwaggerDoc::class.java))
                .build()
                .produces(setOf(MediaType.APPLICATION_JSON_VALUE))
                .consumes(setOf(MediaType.APPLICATION_JSON_VALUE))
                .apiInfo(apiInfo())
                .directModelSubstitute(LocalDateTime::class.java,String::class.java)
        // @formatter:on
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("dongfg's rest api")
            .contact(Contact("dongfg", "https://dongfg.com", "mail#dongfg.com"))
            .version(apiProperty.version)
            .build()
    }
}