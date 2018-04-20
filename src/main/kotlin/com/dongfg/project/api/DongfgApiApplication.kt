package com.dongfg.project.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession

@EnableMongoHttpSession
@SpringBootApplication
class DongfgApiApplication

fun main(args: Array<String>) {
    runApplication<DongfgApiApplication>(*args)
}
