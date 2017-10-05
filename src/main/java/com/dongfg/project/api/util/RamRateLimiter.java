package com.dongfg.project.api.util;

import com.google.common.util.concurrent.RateLimiter;

import java.util.HashMap;
import java.util.Map;

public class RamRateLimiter {

    public static final String LIMIT_KEY_SMS = "sms";

    private static final Map<String, RateLimiter> rateLimiterMap = new HashMap<>();

    static {
        rateLimiterMap.put(LIMIT_KEY_SMS, RateLimiter.create(0.1));
    }

    public static boolean acquire(String limitKey) {
        RateLimiter rateLimiter = rateLimiterMap.get(limitKey);
        return rateLimiter.tryAcquire();
    }

    private RamRateLimiter(){

    }
}
