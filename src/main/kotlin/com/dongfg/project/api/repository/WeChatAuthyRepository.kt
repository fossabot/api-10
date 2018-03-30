package com.dongfg.project.api.repository

import com.dongfg.project.api.model.WeChatAuthy
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * @author dongfg
 * @date 2018/3/29
 */
interface WeChatAuthyRepository : MongoRepository<WeChatAuthy, String> {
    fun findByOpenId(openId: String, sort: Sort): List<WeChatAuthy>
}