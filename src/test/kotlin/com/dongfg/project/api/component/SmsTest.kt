package com.dongfg.project.api.component

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner


/**
 * @author dongfg
 * @date 2018/3/16
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("dev")
internal class SmsTest {

    @Autowired
    lateinit var sms: Sms

    @Test
    fun templates() {
        println(sms.templates())
    }

    @Test
    fun send() {
        println(sms.send("12345678900", "【网站通知】2018-03-16\n任务：kotlin-test\n状态：test-passed"))
    }
}