package com.dongfg.project.api.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author dongfg
 * @date 17-11-8
 */
@Component
public class HttpProxyExecutor {

    @Value("${https.proxyHost}")
    private String httpsProxyHost;

    @Value("${https.proxyPort}")
    private String httpsProxyPort;

    public void executeWithProxy(Runnable function) {
        try {
            System.setProperty("https.proxyHost", httpsProxyHost);
            System.setProperty("https.proxyPort", httpsProxyPort);
            function.run();
        } finally {
            System.clearProperty("https.proxyHost");
            System.clearProperty("https.proxyPort");
        }
    }
}
