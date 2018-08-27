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
    var poster: String? = null,
    @ApiModelProperty("rss地址")
    var rssLink: String? = null,
    @ApiModelProperty("译名")
    var cnName: String? = null,
    @ApiModelProperty("原名")
    var enName: String? = null,
    @ApiModelProperty("连载状态")
    var playStatus: String? = null,
    @ApiModelProperty("地区")
    var area: String? = null,
    @ApiModelProperty("类型")
    var category: String? = null,
    @ApiModelProperty("评分人数")
    var scoreCount: Int? = null,
    @ApiModelProperty("评分详情(从5到1)")
    var scoreDetail: List<Int>? = null
)