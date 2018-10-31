package com.dongfg.project.api

import org.mockito.Mockito

fun <T> any(): T {
    return Mockito.any<T>()
}
