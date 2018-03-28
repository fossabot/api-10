package com.dongfg.project.api.repository

import com.dongfg.project.api.model.WeChatUser
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

/**
 * @author dongfg
 * @date 2018/3/28
 */
interface WeChatUserRepository : MongoRepository<WeChatUser, String> {
    fun findByToken(token: String): Optional<WeChatUser>
}