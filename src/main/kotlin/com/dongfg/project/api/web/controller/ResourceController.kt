package com.dongfg.project.api.web.controller

import com.dongfg.project.api.common.util.EnableSwaggerDoc
import com.dongfg.project.api.component.ZiMuZu
import com.dongfg.project.api.model.ResourceDetail
import com.dongfg.project.api.model.ResourceInfo
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
    private lateinit var ziMuZu: ZiMuZu

    @GetMapping("/{keyword}")
    @ApiOperation("剧集搜索")
    fun resources(@PathVariable keyword: String): List<ResourceInfo> {
        return ziMuZu.search(keyword)
    }

    @GetMapping("/detail/{resourceId}")
    @ApiOperation("剧集详情查看")
    fun resourceDetail(@PathVariable resourceId: String): ResourceDetail {
        return ziMuZu.detail(resourceId)
    }
}