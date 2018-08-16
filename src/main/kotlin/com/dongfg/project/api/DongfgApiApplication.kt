package com.dongfg.project.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@EnableJpaRepositories(basePackages = ["com.dongfg.project.api.repository"],
        includeFilters = [ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = [(JpaRepository::class)])])
@EnableMongoRepositories(basePackages = ["com.dongfg.project.api.repository"],
        includeFilters = [ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = [(MongoRepository::class)])])
@SpringBootApplication
class DongfgApiApplication

fun main(args: Array<String>) {
    runApplication<DongfgApiApplication>(*args)
}
