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
}