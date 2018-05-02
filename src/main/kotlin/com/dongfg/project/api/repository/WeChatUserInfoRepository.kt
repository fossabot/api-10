package com.dongfg.project.api.repository

import com.dongfg.project.api.model.WeChatUserInfo
import org.springframework.data.mongodb.repository.MongoRepository

interface WeChatUserInfoRepository : MongoRepository<WeChatUserInfo, String>