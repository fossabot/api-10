package com.dongfg.project.api.web.controller

import com.dongfg.project.api.any
import com.dongfg.project.api.common.config.JacksonConfig
import com.dongfg.project.api.common.util.toFormatString
import com.dongfg.project.api.model.Comment
import com.dongfg.project.api.service.CommentService
import com.dongfg.project.api.web.payload.GenericPayload
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.core.Is.`is`
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@RunWith(SpringRunner::class)
@WebMvcTest(CommentController::class, secure = false)
@Import(JacksonConfig::class)
@ActiveProfiles("test")
class CommentControllerTest {

    @MockBean
    private lateinit var commentService: CommentService

    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun comments() {
        val createTime = LocalDateTime.now()
        given(commentService.comments())
            .willReturn(arrayListOf(Comment(name = "junit4", comment = "hello", createTime = createTime)))

        mockMvc.perform(get("/comment").accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].name", `is`("junit4")))
            .andExpect(jsonPath("$[0].comment", `is`("hello")))
            .andExpect(jsonPath("$[0].createTime", `is`(createTime.toFormatString())))
    }

    @Test
    fun createComment() {
        val comment = Comment(name = "junit4", comment = "hello")

        given(commentService.createComment(any()))
            .willReturn(GenericPayload(true))

        mockMvc.perform(
            post("/comment")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(comment))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.code", `is`(0)))
    }
}