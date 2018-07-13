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