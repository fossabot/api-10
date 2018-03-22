package com.dongfg.project.api

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
    @get:Rule
    val restDocumentation = JUnitRestDocumentation()

    @Autowired
    private val context: WebApplicationContext? = null


    private var mockMvc: MockMvc? = null

    @Before
    fun setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context!!)
                .apply<DefaultMockMvcBuilder>(documentationConfiguration(this.restDocumentation))
                .build()
    }

    @Test
    @Throws(Exception::class)
    fun webHooksTravisci() {

        this.mockMvc?.perform(post("/webhooks/travisci")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Travis-Repo-Slug", "dongfg/wiki")
                .param("payload", "passed").accept(MediaType.APPLICATION_JSON))
                ?.andExpect(status().isOk)
                ?.andDo(document("travisci"))
    }
}