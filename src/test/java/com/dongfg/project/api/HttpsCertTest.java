package com.dongfg.project.api;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

/**
 * @author dongfg
 * @date 18-1-25
 */
public class HttpsCertTest {

    @Test
    public void certInfo() throws Exception {
        URL url = new URL("https://dongfg.com");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.connect();
        Certificate[] certs = connection.getServerCertificates();
        for (Certificate cert : certs) {
            //System.out.println(new String(cert.getEncoded()));
            System.out.println(JSON.toJSONString(cert));
            X509Certificate x509Certificate = (X509Certificate) cert;
            System.out.println(x509Certificate.getNotBefore());
            System.out.println(x509Certificate.getNotAfter());
        }
    }

}
