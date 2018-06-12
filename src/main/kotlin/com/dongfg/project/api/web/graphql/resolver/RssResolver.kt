package com.dongfg.project.api.web.graphql.resolver

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.dongfg.project.api.component.TinyTinyRss
import com.dongfg.project.api.service.RssService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RssResolver : GraphQLQueryResolver {
    @Autowired
    private lateinit var rssService: RssService

    fun feeds(): List<TinyTinyRss.Category> {
        return rssService.feeds()
    }
}