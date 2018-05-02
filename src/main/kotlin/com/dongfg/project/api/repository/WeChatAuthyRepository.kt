package com.dongfg.project.api.repository

import com.dongfg.project.api.model.WeChatAuthy
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository

interface WeChatAuthyRepository : JpaRepository<WeChatAuthy, String> {
    fun findByOpenId(openId: String, sort: Sort): List<WeChatAuthy>
}