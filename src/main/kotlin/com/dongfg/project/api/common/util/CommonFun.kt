package com.dongfg.project.api.common.util

/**
 * @author dongfg
 * @date 2018/3/22
 */

inline fun <T : Any, R> whenNotNull(input: T?, callback: (T) -> R): R? {
    return input?.let(callback)
}