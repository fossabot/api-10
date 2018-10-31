package com.dongfg.project.api.web.controller

import com.dongfg.project.api.any
import com.dongfg.project.api.common.config.JacksonConfig
import com.dongfg.project.api.common.util.toFormatString
import com.dongfg.project.api.model.ResourceDetail
import com.dongfg.project.api.model.ResourceEpisode
import com.dongfg.project.api.model.ResourceInfo
import com.dongfg.project.api.service.ResourceService
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.CoreMatchers.`is`
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@RunWith(SpringRunner::class)
@WebMvcTest(ResourceController::class, secure = false)
@Import(JacksonConfig::class)
@ActiveProfiles("test")
class ResourceControllerTest {

    @MockBean
    private lateinit var resourceService: ResourceService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun resources() {
        val publishTime = LocalDateTime.now()
        val updateTime = LocalDateTime.now()
        given(resourceService.search(any()))
            .willReturn(arrayListOf(ResourceInfo().apply {
                id = "111"
                name = "link"
                link = "http://mock.com/res/111"
                poster = "http://pic.mock.com/p.jpg"
                this.publishTime = publishTime
                this.updateTime = updateTime
            }))

        mockMvc.perform(get("/resource/junit4"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].link", `is`("http://mock.com/res/111")))
            .andExpect(jsonPath("$[0].publishTime", `is`(publishTime.toFormatString())))
            .andExpect(jsonPath("$[0].updateTime", `is`(updateTime.toFormatString())))
    }

    @Test
    fun resourceDetail() {
        given(resourceService.detail(any()))
            .willReturn(ResourceDetail(id = "111").apply {
                enName = "Name"
                cnName = "名字"
                scoreDetail = arrayListOf(5, 4, 3, 2, 1)
            })

        mockMvc.perform(get("/resource/detail/111"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.enName", `is`("Name")))
            .andExpect(jsonPath("$.cnName", `is`("名字")))
            .andExpect(jsonPath("$.scoreDetail", `is`(arrayListOf(5, 4, 3, 2, 1))))
    }

    @Test
    fun episodes() {
        val publishTime = LocalDateTime.now()
        given(resourceService.episodes(any()))
            .willReturn(
                arrayListOf(
                    ResourceEpisode("11101", "111", "Name").apply {
                        ed2k = "ed2k"
                        magnet = "magent"
                        this.publishTime = publishTime
                    }
                )
            )

        mockMvc.perform(get("/resource/episodes/111"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.[0].id", `is`("11101")))
            .andExpect(jsonPath("$.[0].ed2k", `is`("ed2k")))
            .andExpect(jsonPath("$.[0].publishTime", `is`(publishTime.toFormatString())))
    }
}