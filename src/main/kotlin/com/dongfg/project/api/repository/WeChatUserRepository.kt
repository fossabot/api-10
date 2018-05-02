package com.dongfg.project.api.repository

import com.dongfg.project.api.model.WeChatUser
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface WeChatUserRepository : JpaRepository<WeChatUser, String> {
    fun findByToken(token: String): Optional<WeChatUser>
}