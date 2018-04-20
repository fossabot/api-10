package com.dongfg.project.api.common.config

import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/**
 * @author dongfg
 * @date 2018/3/17
 */
@Configuration
class GraphqlConfig {

    companion object {
        const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    }

    @Bean
    fun graphqlDate(): GraphqlDate {
        return GraphqlDate()
    }

    inner class GraphqlDate : GraphQLScalarType("LocalDateTime", "Java8 DateTime Type", object : Coercing<LocalDateTime, String> {
        override fun serialize(dataFetcherResult: Any): String {
            if (dataFetcherResult is LocalDateTime) {
                return DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(dataFetcherResult)
            }
            throw CoercingSerializeException("Invalid value '$dataFetcherResult' for LocalDateTime")
        }

        override fun parseValue(input: Any): LocalDateTime? {
            if (input is String) {
                val date = LocalDateTime.parse(input, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
                if (date != null) {
                    return date
                }
            }
            throw CoercingParseValueException("Invalid value '$input' for LocalDateTime")
        }

        override fun parseLiteral(input: Any): LocalDateTime? {
            if (input !is StringValue) {
                return null
            }
            return LocalDateTime.parse(input.value, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
        }
    })
}