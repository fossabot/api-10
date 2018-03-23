package com.dongfg.project.api.web.payload

/**
 * @author dongfg
 * @date 2018/3/23
 */
data class GenericPayload(
        var success: Boolean,
        var msg: String? = null,
        var data: String? = null
)