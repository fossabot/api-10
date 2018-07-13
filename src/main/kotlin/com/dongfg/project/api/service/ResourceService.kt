package com.dongfg.project.api.service

import com.dongfg.project.api.component.ZiMuZu
import com.dongfg.project.api.model.ResourceEpisode
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ResourceService {
    companion object : KLogging()

    @Autowired
    private lateinit var ziMuZu: ZiMuZu

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
     * rule2: 选择更高清的资源
     */
    private fun chooseEpisode(episodes: List<ResourceEpisode>): ResourceEpisode {
        // TODO
        return episodes[0]
    }
}