package com.dongfg.project.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class DongfgApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DongfgApiApplication.class, args);
    }
}
