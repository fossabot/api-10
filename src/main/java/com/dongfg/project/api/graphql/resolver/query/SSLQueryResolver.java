package com.dongfg.project.api.graphql.resolver.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dongfg.project.api.graphql.type.SSLCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class SSLQueryResolver implements GraphQLQueryResolver {

    public String systemTime() {
        return LocalDateTime.now().toString();
    }

    public SSLCertificate sslCert(String domain) throws IOException {
        SSLCertificate sslCert = new SSLCertificate();
        sslCert.setDomain(domain);
        HttpsURLConnection conn = (HttpsURLConnection) new URL("https://" + domain).openConnection();
        conn.connect();
        conn.setHostnameVerifier((s, sslSession) -> true);
        Certificate[] certs = conn.getServerCertificates();
        if (certs.length > 1) {
            Certificate cert = certs[0];
            if (cert instanceof X509Certificate) {
                Date expireAt = ((X509Certificate) cert).getNotAfter();
                sslCert.setExpireAt(expireAt);
            }
        }
        return sslCert;
    }
}
