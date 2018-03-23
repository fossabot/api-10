package com.dongfg.project.api

import com.dongfg.project.api.common.Constants
import com.dongfg.project.api.component.Totp
import com.dongfg.project.api.model.Comment
import com.dongfg.project.api.repository.CommentRepository
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KLogging
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

/**
 * @author dongfg
 * @date 2018/3/19
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("dev")
@FixMethodOrder(MethodSorters.JVM)
class ApiDocumentation {

    companion object : KLogging()

    @get:Rule
    val restDocumentation = JUnitRestDocumentation()

    @Autowired
    private lateinit var commentRepository: CommentRepository

    @Autowired
    private val context: WebApplicationContext? = null

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var totpMock: Totp

    private var mockMvc: MockMvc? = null

    @Before
    fun setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context!!)
                .apply<DefaultMockMvcBuilder>(documentationConfiguration(this.restDocumentation))
                .build()
    }

    @Test
    @Throws(Exception::class)
    fun createComment() {
        val input = HashMap<String, String>(3)
        input["comment"] = "restdoc"
        input["name"] = "dongfg"
        input["email"] = "dongfg@webmaster"
        println(objectMapper.writeValueAsString(input))

        this.mockMvc?.perform(post("/comment")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(input)))
                ?.andExpect(status().isOk)
                ?.andDo(document("createComment",
                        requestFields(
                                fieldWithPath("comment").description("评论"),
                                fieldWithPath("name").description("昵称"),
                                fieldWithPath("email").description("邮箱地址")),
                        responseFields(
                                fieldWithPath("success").description("成功失败标志"),
                                fieldWithPath("msg").description("返回消息")
                        )
                ))
    }

    @Test
    @Throws(Exception::class)
    fun comments() {
        this.mockMvc?.perform(get("/comment")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                ?.andExpect(status().isOk)
                ?.andDo(document("comments",
                        responseFields(
                                fieldWithPath("[].comment").description("评论"),
                                fieldWithPath("[].name").description("昵称"),
                                fieldWithPath("[].email").optional().description("邮箱地址"),
                                fieldWithPath("[].createTime").description("创建时间")
                        )
                ))
    }

    @Test
    @Throws(Exception::class)
    fun sendMessage() {
        val input = HashMap<String, String>(3)
        input["type"] = Constants.MessageType.EMAIL.name
        input["level"] = Constants.MessageLevel.ERROR.name
        input["receiver"] = "dongfg@webmaster"
        input["title"] = "restdoc"
        input["content"] = "send from dongfg-api"
        input["catalog"] = "test"
        println(objectMapper.writeValueAsString(input))

        Mockito.`when`(totpMock.validate("123456")).thenReturn(true)

        this.mockMvc?.perform(post("/message")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .header("totp-auth-token", "123456")
                .content(objectMapper.writeValueAsString(input)))
                ?.andDo(MockMvcResultHandlers.print())
                ?.andExpect(status().isOk)
                ?.andDo(document("sendMessage",
                        requestFields(
                                fieldWithPath("type").description(Constants.MessageType.values()),
                                fieldWithPath("level").description(Constants.MessageLevel.values()),
                                fieldWithPath("receiver").description("接受者"),
                                fieldWithPath("title").description("标题"),
                                fieldWithPath("content").description("内容"),
                                fieldWithPath("catalog").description("分类")),
                        responseFields(
                                fieldWithPath("success").description("成功失败标志"),
                                fieldWithPath("msg").description("返回消息")
                        )
                ))

    }
}