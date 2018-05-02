package com.dongfg.project.api.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "wechat_user")
data class WeChatUserInfo(
        @Id
        val openId: String,
        val nickName: String,
        val gender: Int,
        val language: String,
        val city: String,
        val province: String,
        val country: String,
        val avatarUrl: String
)