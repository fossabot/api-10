package com.dongfg.project.api.util;

import com.google.common.util.concurrent.RateLimiter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dongfg
 * @date 2017/10/15
 */
public class RamRateLimiter {

    public static final String LIMIT_KEY_SMS = "sms";

    private static final Map<String, RateLimiter> RATE_LIMITER_MAP = new HashMap<>();

    static {
        RATE_LIMITER_MAP.put(LIMIT_KEY_SMS, RateLimiter.create(0.1));
    }

    public static boolean acquire(String limitKey) {
        RateLimiter rateLimiter = RATE_LIMITER_MAP.get(limitKey);
        return rateLimiter.tryAcquire();
    }

    private RamRateLimiter() {

    }
}
