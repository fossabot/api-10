package com.dongfg.project.api

import de.flapdoodle.embed.mongo.Command
import de.flapdoodle.embed.mongo.config.DownloadConfigBuilder
import de.flapdoodle.embed.mongo.config.ExtractedArtifactStoreBuilder
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder
import de.flapdoodle.embed.process.config.IRuntimeConfig
import de.flapdoodle.embed.process.config.store.TimeoutConfigBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class MongoTestConfig {
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
}