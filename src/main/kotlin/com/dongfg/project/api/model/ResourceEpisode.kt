package com.dongfg.project.api.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "resource_episode")
@ApiModel("单集剧集信息")
data class ResourceEpisode(
        @Id
        @ApiModelProperty("单集剧集ID")
        val id: String,
        @ApiModelProperty("剧集ID")
        val resourceId: String,
        @ApiModelProperty("剧集名称")
        val name: String,
        @ApiModelProperty("季")
        val season: Int,
        @ApiModelProperty("集")
        val episode: Int,
        @ApiModelProperty("电驴下载地址")
        val ed2k: String?,
        @ApiModelProperty("磁力下载地址")
        val magnet: String?,
        @ApiModelProperty("发布时间")
        val publishTime: LocalDateTime
)