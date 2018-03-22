package com.dongfg.project.api

import com.dongfg.project.api.repository.CommentRepository
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KLogging
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
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

    private var mockMvc: MockMvc? = null

    @Before
    fun setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context!!)
                .apply<DefaultMockMvcBuilder>(documentationConfiguration(this.restDocumentation))
                .build()
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
                                fieldWithPath("comment").description("评论"),
                                fieldWithPath("name").description("昵称"),
                                fieldWithPath("email").description("邮箱地址"),
                                fieldWithPath("createTime").description("创建时间")
                        )
                ))
    }
}