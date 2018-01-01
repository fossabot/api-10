package com.dongfg.project.api.util;

/**
 * 常量
 *
 * @author dongfg
 * @date 17-11-22
 */
public interface Constants {
    interface CodeConstants {
        /**
         * 成功
         */
        int SUCCESS = 0;

        /**
         * 失败
         */
        int FAIL = 1;

        /**
         * 参数错误
         */
        int PARAM_ERROR = 2;

        /**
         * 网络错误
         */
        int NETWORK_ERROR = 999;
    }

    interface MessageLevel {
        String INFO = "INFO";
        String WARN = "WARN";
        String ERROR = "ERROR";
    }
}
