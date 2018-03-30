package com.dongfg.project.api.common.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * @author dongfg
 * @date 2018/3/22
 */
@Configuration
class JacksonConfig {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    @Primary
    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper().registerKotlinModule()

        val javaTimeModule = JavaTimeModule()
        javaTimeModule.addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer(formatter))
        javaTimeModule.addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer(formatter))
        objectMapper.registerModule(javaTimeModule)

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)

        return objectMapper
    }

    @Bean
    fun jackson2ObjectMapperFactoryBean(): Jackson2ObjectMapperFactoryBean {
        return Jackson2ObjectMapperFactoryBean()
    }
}