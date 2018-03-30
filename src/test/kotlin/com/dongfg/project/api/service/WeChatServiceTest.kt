package com.dongfg.project.api.service

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author dongfg
 * @date 2018/3/29
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("dev")
class WeChatServiceTest {

    @Autowired
    private lateinit var weChatService: WeChatService

    @Test
    fun login() {
        val code = ""
        val (success, msg, token) = weChatService.login(code)

        Assert.assertTrue(success)
        Assert.assertNotNull(token)

        println(token)
    }

    @Test
    fun listAuthy() {
        weChatService.listAuthy()
    }

    @Test
    fun saveOrUpdateAuthy() {
    }

    @Test
    fun checkToken() {
    }
}