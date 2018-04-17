package com.dongfg.project.api.web.payload

import com.dongfg.project.api.common.Constants.PayloadCode
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * @author dongfg
 * @date 2018/3/23
 */
@ApiModel
class GenericPayload constructor(
        @ApiModelProperty("success flag")
        var success: Boolean,
        @ApiModelProperty("error code")
        var code: Int? = null,
        @ApiModelProperty("response msg")
        var msg: String? = null,
        @ApiModelProperty("response data")
        var data: String? = null) {
    init {
        if (this.code == null) {
            this.code = if (success) PayloadCode.SUCCESS.code else PayloadCode.FAILURE.code
        }
    }
}