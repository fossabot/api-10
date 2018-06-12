package com.dongfg.project.api.web.controller

import com.dongfg.project.api.common.util.EnableSwaggerDoc
import com.dongfg.project.api.component.TinyTinyRss
import com.dongfg.project.api.service.RssService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@EnableSwaggerDoc
@RequestMapping("/rss")
@RestController
class RssController {

    @Autowired
    private lateinit var rssService: RssService

    @GetMapping("feeds")
    @ApiOperation("订阅列表")
    fun listFeed(): List<TinyTinyRss.Category> {
        return rssService.feeds()
    }
}