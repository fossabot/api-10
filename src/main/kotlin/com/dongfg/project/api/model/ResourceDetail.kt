package com.dongfg.project.api.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "resource_detail")
@ApiModel("剧集详细信息")
data class ResourceDetail(
        @Id
        @ApiModelProperty("剧集ID")
        val id: String,
        @ApiModelProperty("封面图片")
        val poster: String,
        @ApiModelProperty("rss地址")
        val rssLink: String,
        @ApiModelProperty("译名")
        val cnName: String,
        @ApiModelProperty("原名")
        val enName: String,
        @ApiModelProperty("连载状态")
        val playStatus: String,
        @ApiModelProperty("地区")
        val area: String,
        @ApiModelProperty("类型")
        val category: String,
        @ApiModelProperty("评分人数")
        val scoreCount: Int,
        @ApiModelProperty("评分详情(从5到1)")
        val scoreDetail: List<Int>
)