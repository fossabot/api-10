package com.dongfg.project.api.common.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories


@EnableMongoRepositories(
    basePackages = ["com.dongfg.project.api.repository"],
    includeFilters = [ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = [(MongoRepository::class)])]
)
@Configuration
class RepositoryConfig {
}