package com.dongfg.project.api.common.util

import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


inline fun <T : Any, R> whenNotNull(input: T?, callback: (T) -> R): R? {
    return input?.let(callback)
}

class Json() : JSONObject() {
    constructor(init: Json.() -> Unit) : this() {
        this.init()
    }

    infix fun <T> String.to(value: T) {
        put(this, value)
    }
}

fun LocalDateTime.toFormatString(): String {
    return this.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}