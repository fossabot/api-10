package com.dongfg.project.api.common.util

import com.dongfg.project.api.common.Constants
import com.dongfg.project.api.component.Totp
import org.json.JSONObject


inline fun <T : Any, R> whenNotNull(input: T?, callback: (T) -> R): R? {
    return input?.let(callback)
}

inline fun <T> Totp.whenInvalid(totpCode: String, callback: (String) -> T): T? {
    return when {
        !RamRateLimiter.acquire(Constants.RateLimitKey.MESSAGE) -> callback("rate limit exceeded")
        !validate(totpCode) -> callback("invalid totp")
        else -> null
    }
}

class Json() : JSONObject() {
    constructor(init: Json.() -> Unit) : this() {
        this.init()
    }

    infix fun <T> String.to(value: T) {
        put(this, value)
    }
}