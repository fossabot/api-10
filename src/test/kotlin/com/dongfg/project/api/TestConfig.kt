package com.dongfg.project.api

import de.flapdoodle.embed.mongo.Command
import de.flapdoodle.embed.mongo.config.DownloadConfigBuilder
import de.flapdoodle.embed.mongo.config.ExtractedArtifactStoreBuilder
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder
import de.flapdoodle.embed.process.config.IRuntimeConfig
import de.flapdoodle.embed.process.config.store.TimeoutConfigBuilder
import org.mockito.Mockito
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import redis.embedded.RedisServer
import java.io.IOException
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import javax.sql.DataSource


@Configuration
class TestConfig {

    private lateinit var redisServer: RedisServer

    @Bean
    fun runtimeConfig(): IRuntimeConfig {
        val command = Command.MongoD

        return RuntimeConfigBuilder()
            .defaults(command)
            .artifactStore(
                ExtractedArtifactStoreBuilder()
                    .defaults(command)
                    .download(
                        DownloadConfigBuilder()
                            .defaultsForCommand(command)
                            .timeoutConfig(
                                TimeoutConfigBuilder()
                                    .connectionTimeout(1000 * 600)
                                    .readTimeout(1000 * 600).build()
                            )
                            .downloadPath("http://downloads.mongodb.org/").build()
                    )
            )
            .build()
    }

    @Bean
    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName("org.h2.Driver")
        dataSource.url = "jdbc:h2:mem:db;DB_CLOSE_DELAY=-1"
        dataSource.username = "sa"
        dataSource.password = "sa"
        return dataSource
    }


    @PostConstruct
    @Throws(IOException::class)
    fun startRedis() {
        redisServer = RedisServer()
        redisServer.start()
    }

    @PreDestroy
    fun stopRedis() {
        redisServer.stop()
    }
}

fun <T> any(): T {
    return Mockito.any<T>()
}