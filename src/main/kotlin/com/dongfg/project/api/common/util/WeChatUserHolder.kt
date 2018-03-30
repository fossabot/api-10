package com.dongfg.project.api.common.util

import com.dongfg.project.api.model.WeChatUser

/**
 * @author dongfg
 * @date 2018/3/29
 */
class WeChatUserHolder {
    companion object {
        var threadLocal = ThreadLocal<WeChatUser>()

        @JvmStatic
        fun set(user: WeChatUser) {
            threadLocal.set(user)
        }

        @JvmStatic
        fun getCurrent(): WeChatUser {
            return threadLocal.get()
        }
    }
}