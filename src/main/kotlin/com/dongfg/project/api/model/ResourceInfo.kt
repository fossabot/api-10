package com.dongfg.project.api.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "resource_info")
@ApiModel("剧集信息")
data class ResourceInfo(
    @Id
    @ApiModelProperty("剧集ID")
    var id: String? = null,
    @ApiModelProperty("剧集名称")
    var name: String? = null,
    @ApiModelProperty("剧集链接")
    var link: String? = null,
    @ApiModelProperty("封面图片")
    var poster: String? = null,
    @ApiModelProperty("发布时间")
    var publishTime: LocalDateTime? = null,
    @ApiModelProperty("更新时间")
    var updateTime: LocalDateTime? = null
)