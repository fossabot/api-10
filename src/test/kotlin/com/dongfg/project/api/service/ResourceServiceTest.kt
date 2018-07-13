package com.dongfg.project.api.service

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("dev")
class ResourceServiceTest {

    @Autowired
    private lateinit var resourceService: ResourceService

    @Test
    fun episodes() {
        resourceService.episodes("31892")
    }
}