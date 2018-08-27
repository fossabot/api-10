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
    var season: Int? = null,
    @ApiModelProperty("集")
    var episode: Int? = null,
    @ApiModelProperty("电驴下载地址")
    var ed2k: String? = null,
    @ApiModelProperty("磁力下载地址")
    var magnet: String? = null,
    @ApiModelProperty("发布时间")
    var publishTime: LocalDateTime? = null
)