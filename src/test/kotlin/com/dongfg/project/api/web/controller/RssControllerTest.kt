package com.dongfg.project.api.web.controller

import com.dongfg.project.api.component.TinyTinyRss
import com.dongfg.project.api.service.RssService
import org.hamcrest.CoreMatchers.`is`
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringRunner::class)
@WebMvcTest(RssController::class, secure = false)
@ActiveProfiles("test")
class RssControllerTest {

    @MockBean
    private lateinit var rssService: RssService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun listFeed() {
        given(rssService.feeds())
            .willReturn(
                arrayListOf(
                    TinyTinyRss.Category(id = "6", title = "软件资讯", unread = 1).apply {
                        feeds = arrayListOf(
                            TinyTinyRss.Feed(id = "2", title = "少数派", unread = 5, url = "https://rss.dongfg.com")
                        )
                    }
                )
            )

        mockMvc.perform(get("/rss/feeds"))
            .andDo {
                println(it.response.contentAsString)
            }
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id", `is`("6")))
            .andExpect(jsonPath("$[0].title", `is`("软件资讯")))
            .andExpect(jsonPath("$[0].unread", `is`(1)))
            .andExpect(jsonPath("$[0].feeds[0].title", `is`("少数派")))
            .andExpect(jsonPath("$[0].feeds[0].unread", `is`(5)))
    }
}