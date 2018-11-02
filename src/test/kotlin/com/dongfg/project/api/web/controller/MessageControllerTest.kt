package com.dongfg.project.api.web.controller

import com.dongfg.project.api.any
import com.dongfg.project.api.common.Constants
import com.dongfg.project.api.common.config.JacksonConfig
import com.dongfg.project.api.common.config.WebSecurityConfig
import com.dongfg.project.api.component.Totp
import com.dongfg.project.api.model.Message
import com.dongfg.project.api.service.MessageService
import com.dongfg.project.api.web.payload.GenericPayload
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockkObject
import org.hamcrest.Matchers.`is`
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@RunWith(SpringRunner::class)
@WebMvcTest(MessageController::class, secure = true)
@ActiveProfiles("test")
@Import(JacksonConfig::class, WebSecurityConfig::class)
class MessageControllerTest {

    @MockBean
    private lateinit var messageService: MessageService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun staticMock() {
        mockkObject(Totp)
        every { Totp.validate(any()) } returns true

        Assert.assertTrue(Totp.validate("123456"))
    }

    @Test
    fun sendMessage_valid() {
        val totp = "123456"

        mockkObject(Totp)
        every {
            Totp.validate(totp)
        } returns true

        given(messageService.sendMessage(any()))
            .willReturn(GenericPayload(true))

        val sendTime = LocalDateTime.now()

        val message = Message(
            type = Constants.MessageType.SMS,
            level = Constants.MessageLevel.DEBUG,
            receiver = "138000012345",
            title = "单元测试",
            content = "测试通过",
            catalog = "测试",
            time = sendTime
        )

        mockMvc.perform(
            post("/message")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("X-Auth-Totp", totp)
                .content(objectMapper.writeValueAsString(message))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
    }

    @Test
    fun sendMessage_invalid() {
        val totp = "123456"

        mockkObject(Totp)
        every {
            Totp.validate(totp)
        } returns false

        given(messageService.sendMessage(any()))
            .willReturn(GenericPayload(true))

        val sendTime = LocalDateTime.now()

        val message = Message(
            type = Constants.MessageType.SMS,
            level = Constants.MessageLevel.DEBUG,
            receiver = "138000012345",
            title = "单元测试",
            content = "测试通过",
            catalog = "测试",
            time = sendTime
        )

        mockMvc.perform(
            post("/message")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("X-Auth-Totp", totp)
                .content(objectMapper.writeValueAsString(message))
        )
            .andDo {
                println(it.response.contentAsString)
            }
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(false)))
            .andExpect(jsonPath("$.msg", `is`("totp 错误")))
    }

    @Test
    fun sendMessage_missingHeader() {
        val totp = "123456"

        mockkObject(Totp)
        every {
            Totp.validate(totp)
        } returns false

        given(messageService.sendMessage(any()))
            .willReturn(GenericPayload(true))

        val sendTime = LocalDateTime.now()

        val message = Message(
            type = Constants.MessageType.SMS,
            level = Constants.MessageLevel.DEBUG,
            receiver = "138000012345",
            title = "单元测试",
            content = "测试通过",
            catalog = "测试",
            time = sendTime
        )

        mockMvc.perform(
            post("/message")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(message))
        )
            .andDo {
                println(it.response.contentAsString)
            }
            .andExpect(status().`is`(403))
            .andExpect(jsonPath("$.success", `is`(false)))
            .andExpect(jsonPath("$.msg", `is`("未授权的访问")))
    }
}