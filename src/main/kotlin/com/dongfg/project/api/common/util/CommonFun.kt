package com.dongfg.project.api.common.util

import com.dongfg.project.api.common.Constants
import com.dongfg.project.api.component.Totp

/**
 * @author dongfg
 * @date 2018/3/22
 */

inline fun <T : Any, R> whenNotNull(input: T?, callback: (T) -> R): R? {
    return input?.let(callback)
}

inline fun <T> Totp.whenInvalid(totpCode: String, callback: (String) -> T): T? {
    if (!RamRateLimiter.acquire(Constants.RateLimitKey.MESSAGE)) {
        return callback("rate limit exceeded")
    } else if (!validate(totpCode)) {
        return callback("invalid totp")
    } else {
        return null
    }
}