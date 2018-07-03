package com.dongfg.project.api.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

@ApiModel("剧集信息")
data class ResourceInfo(
        @ApiModelProperty("剧集ID")
        val id: String,
        @ApiModelProperty("剧集名称")
        val name: String,
        @ApiModelProperty("剧集链接")
        val link: String,
        @ApiModelProperty("封面图片")
        val poster: String,
        @ApiModelProperty("发布时间")
        val publishTime: LocalDateTime,
        @ApiModelProperty("更新时间")
        val updateTime: LocalDateTime
)