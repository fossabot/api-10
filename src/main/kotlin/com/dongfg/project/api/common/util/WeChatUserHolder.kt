package com.dongfg.project.api.common.util

import com.dongfg.project.api.model.WeChatUser

class WeChatUserHolder {
    companion object {
        private var threadLocal = ThreadLocal<WeChatUser>()

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