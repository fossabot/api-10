package com.dongfg.project.api.web.payload

import io.swagger.annotations.ApiModelProperty

/**
 * @author dongfg
 * @date 2018/3/28
 */
data class WeChatLoginPayload(
        var success: Boolean,
        var msg: String? = null,
        var token: String? = null
)