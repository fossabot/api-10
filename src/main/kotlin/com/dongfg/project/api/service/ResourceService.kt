package com.dongfg.project.api.service

import com.dongfg.project.api.component.ZiMuZu
import com.dongfg.project.api.model.ResourceDetail
import com.dongfg.project.api.model.ResourceEpisode
import com.dongfg.project.api.model.ResourceInfo
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ResourceService {
    companion object : KLogging()

    @Autowired
    private lateinit var ziMuZu: ZiMuZu

    fun search(keyword: String): List<ResourceInfo> {
        return ziMuZu.search(keyword)
    }

    fun detail(resourceId: String): ResourceDetail {
        return ziMuZu.detail(resourceId)
    }

    fun episodes(resourceId: String): List<ResourceEpisode> {
        // season+episode to list
        return ziMuZu.episodes(resourceId).groupBy {
            "${it.season}-${it.episode}"
        }.values.map {
            chooseEpisode(it)
        }.sortedWith(compareByDescending<ResourceEpisode> { it.season }.thenByDescending { it.episode })
    }

    /**
     * rule1: 选择中文字幕的资源
     * rule2: 选择更高清的资源(TODO)
     */
    private fun chooseEpisode(episodes: List<ResourceEpisode>): ResourceEpisode {
        val filteredEpisodes = episodes.filter { containsChinese(it.name) }
        var theChosenOne = episodes[0]
        if (filteredEpisodes.size > 1) {
            theChosenOne = filteredEpisodes[0]
            logger.info("Multi Resource Found After Filter: ${theChosenOne.resourceId}")
            filteredEpisodes.forEach {
                logger.info("${theChosenOne.resourceId} - ${it.name}")
            }
        } else if (filteredEpisodes.size == 1) {
            theChosenOne = filteredEpisodes[0]
        }
        return theChosenOne
    }

    private fun containsChinese(str: String): Boolean {
        str.toCharArray().forEach {
            if (it >= 0x4E00.toChar() && it <= 0x9FA5.toChar()) {
                return true
            }
        }
        return false
    }
}