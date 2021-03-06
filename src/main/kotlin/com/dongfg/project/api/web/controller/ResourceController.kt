package com.dongfg.project.api.web.controller

import com.dongfg.project.api.common.util.EnableSwaggerDoc
import com.dongfg.project.api.model.ResourceDetail
import com.dongfg.project.api.model.ResourceEpisode
import com.dongfg.project.api.model.ResourceInfo
import com.dongfg.project.api.service.ResourceService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@EnableSwaggerDoc
@RequestMapping("/resource")
@RestController
class ResourceController {
    @Autowired
    private lateinit var resourceService: ResourceService

    @GetMapping("/{keyword}")
    @ApiOperation("剧集搜索")
    fun resources(@PathVariable keyword: String): List<ResourceInfo> {
        return resourceService.search(keyword)
    }

    @GetMapping("/detail/{resourceId}")
    @ApiOperation("剧集详情查看")
    fun resourceDetail(@PathVariable resourceId: String): ResourceDetail {
        return resourceService.detail(resourceId)
    }

    @GetMapping("/episodes/{resourceId}")
    @ApiOperation("剧集分集链接")
    fun episodes(@PathVariable resourceId: String): List<ResourceEpisode> {
        return resourceService.episodes(resourceId)
    }
}