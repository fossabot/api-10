package com.dongfg.project.api.common.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.MediaType
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Configuration
class JacksonConfig {

    private final val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    private final val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @Primary
    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper().registerKotlinModule()

        val javaTimeModule = JavaTimeModule()
        javaTimeModule.addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer(formatter))
        javaTimeModule.addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer(formatter))
        objectMapper.registerModule(javaTimeModule)

        objectMapper.registerModule(JsonOrgModule())

        objectMapper.dateFormat = dateFormat

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        return objectMapper
    }

    @Bean
    fun jackson2ObjectMapperFactoryBean(objectMapper: ObjectMapper): Jackson2ObjectMapperFactoryBean {
        val factoryBean = Jackson2ObjectMapperFactoryBean()
        factoryBean.setObjectMapper(objectMapper)
        return factoryBean
    }

    @Bean
    fun mappingJackson2HttpMessageConverter(objectMapper: ObjectMapper): MappingJackson2HttpMessageConverter {
        val converter = MappingJackson2HttpMessageConverter(objectMapper)
        converter.supportedMediaTypes = arrayListOf(MediaType.ALL)
        return converter
    }
}