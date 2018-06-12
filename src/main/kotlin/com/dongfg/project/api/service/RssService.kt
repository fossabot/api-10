package com.dongfg.project.api.service

import com.dongfg.project.api.component.TinyTinyRss
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RssService {
    @Autowired
    private lateinit var tinyTinyRss: TinyTinyRss

    fun feeds(): List<TinyTinyRss.Category> {
        val categories = tinyTinyRss.getCategories()
        categories.forEach {
            if (it.id == "0") {
                it.title = "未分类"
            }
            it.feeds = tinyTinyRss.getFeeds(it.id)
        }
        return categories
    }
}