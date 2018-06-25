package com.dongfg.project.api.service

import com.dongfg.project.api.model.WeChatUser
import com.dongfg.project.api.repository.WeChatUserInfoRepository
import com.dongfg.project.api.repository.WeChatUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service


@Service
class WeChatService {
    @Autowired
    private lateinit var weChatUserRepository: WeChatUserRepository

    @Autowired
    private lateinit var weChatUserInfoRepository: WeChatUserInfoRepository

    fun listUser(page: PageRequest): Page<WeChatUser> {
        return weChatUserRepository.findAll(page).onEach {
            val user = it
            weChatUserInfoRepository.findById(it.openId).ifPresent { user.userInfo = it }
            user.expireTime = user.updateTime.plusSeconds(7200)
        }
    }
}