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

    interface TravisStatus {
        /**
         * A build has been requested
         */
        String PENDING = "Pending";

        /**
         * The build completed successfully
         */
        String PASSED = "Passed";

        /**
         * The build completed successfully after a previously failed build
         */
        String FIXED = "Fixed";

        /**
         * The build completed in failure after a previously successful build
         */
        String BROKEN = "Broken";

        /**
         * The build is the first build for a new branch and has failed
         */
        String FAILED = "Failed";

        /**
         * The build completed in failure after a previously failed build
         */
        String STILL_FAILING = "Still Failing";

        /**
         * The build was canceled
         */
        String CANCELED = "Canceled";

        /**
         * The build has errored
         */
        String ERRORED = "Errored";
    }

    interface MessageTemplate {
        String TRAVIS_CI_BUILD =
                "Repo: %s\n" +
                        "Build: %s\n" +
                        "Commit: %s";
    }

    interface DateTimeFormat {
        String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    }
}
