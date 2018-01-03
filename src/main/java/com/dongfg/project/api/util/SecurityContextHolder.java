package com.dongfg.project.api.util;

/**
 * @author dongfg
 * @date 2017/11/22
 */
public class SecurityContextHolder {
    private static final ThreadLocal<String> CURRENT_USER = new ThreadLocal<>();

    /**
     * 当前登录用户的openId
     *
     * @return 当前登录用户的openId
     */
    public static String getCurrent() {
        return CURRENT_USER.get();
    }

    /**
     * 保存登录信息
     *
     * @param openId 当前登录用户的openId
     */
    public static void setCurrent(String openId) {
        CURRENT_USER.set(openId);
    }

    /**
     * 及时清理
     */
    public static void removeCurrent() {
        CURRENT_USER.remove();
    }
}
