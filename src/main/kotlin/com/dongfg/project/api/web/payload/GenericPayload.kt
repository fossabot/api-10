package com.dongfg.project.api.web.payload

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * @author dongfg
 * @date 2018/3/23
 */
@ApiModel
data class GenericPayload(
        @ApiModelProperty("success flag")
        var success: Boolean,
        @ApiModelProperty("response msg")
        var msg: String? = null,
        @ApiModelProperty("response data")
        var data: String? = null
)