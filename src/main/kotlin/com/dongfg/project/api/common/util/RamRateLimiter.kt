package com.dongfg.project.api.common.util

import com.dongfg.project.api.common.Constants.RateLimitKey
import com.google.common.util.concurrent.RateLimiter
import java.util.concurrent.ConcurrentHashMap

class RamRateLimiter {
    companion object {
        private val limiterMap = ConcurrentHashMap<RateLimitKey, RateLimiter>()

        init {
            limiterMap[RateLimitKey.MESSAGE] = RateLimiter.create(0.1)
        }

        @JvmStatic
        fun acquire(rateLimitKey: RateLimitKey): Boolean {
            return limiterMap[rateLimitKey]!!.tryAcquire()
        }
    }
}