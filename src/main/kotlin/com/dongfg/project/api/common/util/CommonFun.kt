package com.dongfg.project.api.common.util

import com.dongfg.project.api.component.Totp

/**
 * @author dongfg
 * @date 2018/3/22
 */

inline fun <T : Any, R> whenNotNull(input: T?, callback: (T) -> R): R? {
    return input?.let(callback)
}

inline fun <T> whenTotpInvalid(totp: Totp, totpCode: String, callback: () -> T): T? {
    if (!totp.validate(totpCode)) {
        return callback()
    }
    return null
}