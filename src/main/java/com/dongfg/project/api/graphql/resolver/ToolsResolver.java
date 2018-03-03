package com.dongfg.project.api.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dongfg.project.api.graphql.type.HttpsCert;
import com.dongfg.project.api.util.DateTimeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author dongfg
 * @date 2018/01/25
 */
@Component
@Slf4j
public class ToolsResolver implements GraphQLQueryResolver {

    public HttpsCert httpsCert(String domain) {
        HttpsCert httpsCert = new HttpsCert();
        httpsCert.setDomain(domain);

        try {
            URL url = new URL(domain);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.connect();
            Certificate[] certs = connection.getServerCertificates();
            if (certs.length > 0) {
                X509Certificate x509Certificate = (X509Certificate) certs[0];
                httpsCert.setIssueDate(x509Certificate.getNotBefore());
                httpsCert.setExpireDate(x509Certificate.getNotAfter());
                long expireDays = ChronoUnit.DAYS.between(LocalDateTime.now(),
                        DateTimeConverter.toLocalDateTime(x509Certificate.getNotAfter()));
                httpsCert.setExpireDay(expireDays);
            }
        } catch (IOException e) {
            log.error("HTTPS 证书获取失败", e);
            httpsCert.setExpireDay(-1L);
        }

        return httpsCert;
    }
}
