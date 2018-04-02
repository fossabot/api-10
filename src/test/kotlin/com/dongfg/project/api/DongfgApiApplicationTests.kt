package com.dongfg.project.api

import io.undertow.server.session.SecureRandomSessionIdGenerator
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("dev")
class DongfgApiApplicationTests {

    @Test
    fun contextLoads() {
        val name = "日"
        val sendName = String(name.toByteArray(charset("UTF-8")), charset("GBK"))
        println(sendName)
        val recvName = String(sendName.toByteArray(charset("GBK")), charset("UTF-8"))
        println(recvName)
    }

}
