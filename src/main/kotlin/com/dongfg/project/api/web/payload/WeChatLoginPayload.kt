package com.dongfg.project.api.web.payload

data class WeChatLoginPayload(
        var success: Boolean,
        var msg: String? = null,
        var token: String? = null
)