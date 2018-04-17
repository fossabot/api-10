package com.dongfg.project.api.common

/**
 * @author dongfg
 * @date 2018/3/23
 */
interface Constants {
    enum class MessageType {
        SMS, WEB, APP, EMAIL
    }

    enum class MessageLevel {
        DEBUG, INFO, WARN, ERROR
    }

    enum class RateLimitKey {
        MESSAGE,
    }

    enum class JobAction {
        PAUSE, RESUME, REMOVE, TRIGGER
    }

    class AuthHeader {
        companion object {
            const val TOTP = "x-auth-totp"
            const val WECHAT = "x-auth-wechat"
            const val ADMIN = "x-auth-admin"
        }
    }

    enum class PayloadCode(val code: Int, val msg: String) {
        SUCCESS(0, "成功"),
        FAILURE(1, "失败"),
        NOT_LOGIN(999, "登录失效"),
    }
}