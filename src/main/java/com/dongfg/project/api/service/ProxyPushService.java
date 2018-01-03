package com.dongfg.project.api.service;

import com.google.common.io.BaseEncoding;
import nl.martijndwars.webpush.*;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicHeader;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;

import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author dongfg
 * @date 2018/1/2
 */
public class ProxyPushService extends PushService {

    /**
     * Subject used in the JWT payload (for VAPID)
     */
    private String subject;

    /**
     * The public key (for VAPID)
     */
    private PublicKey publicKey;

    /**
     * The private key (for VAPID)
     */
    private Key privateKey;

    private String httpsProxyHost;

    private int httpsProxyPort;

    /**
     * Send a notification, but don't wait for the response.
     *
     * @param notification web notification
     * @return async HttpResponse
     * @throws GeneralSecurityException GeneralSecurityException
     * @throws IOException              IOException
     * @throws JoseException            JoseException
     */
    @Override
    public Future<HttpResponse> sendAsync(Notification notification) throws GeneralSecurityException, IOException, JoseException {
        BaseEncoding base64url = BaseEncoding.base64Url();

        Encrypted encrypted = encrypt(
                notification.getPayload(),
                notification.getUserPublicKey(),
                notification.getUserAuth(),
                notification.getPadSize()
        );

        byte[] dh = Utils.savePublicKey((ECPublicKey) encrypted.getPublicKey());
        byte[] salt = encrypted.getSalt();

        HttpPost httpPost = new HttpPost(notification.getEndpoint());
        httpPost.addHeader("TTL", String.valueOf(notification.getTTL()));

        Map<String, String> headers = new HashMap<>();

        if (notification.hasPayload()) {
            headers.put("Content-Type", "application/octet-stream");
            headers.put("Content-Encoding", "aesgcm");
            headers.put("Encryption", "keyid=p256dh;salt=" + base64url.omitPadding().encode(salt));
            headers.put("Crypto-Key", "keyid=p256dh;dh=" + base64url.encode(dh));

            httpPost.setEntity(new ByteArrayEntity(encrypted.getCiphertext()));
        }

        if (vapidEnabled() && !notification.isGcm()) {
            JwtClaims claims = new JwtClaims();
            claims.setAudience(notification.getOrigin());
            claims.setExpirationTimeMinutesInTheFuture(12 * 60);
            claims.setSubject(subject);

            JsonWebSignature jws = new JsonWebSignature();
            jws.setHeader("typ", "JWT");
            jws.setHeader("alg", "ES256");
            jws.setPayload(claims.toJson());
            jws.setKey(privateKey);
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256);

            headers.put("Authorization", "WebPush " + jws.getCompactSerialization());

            byte[] pk = Utils.savePublicKey((ECPublicKey) publicKey);

            if (headers.containsKey("Crypto-Key")) {
                headers.put("Crypto-Key", headers.get("Crypto-Key") + ";p256ecdsa=" + base64url.omitPadding().encode(pk));
            } else {
                headers.put("Crypto-Key", "p256ecdsa=" + base64url.encode(pk));
            }
        }

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpPost.addHeader(new BasicHeader(entry.getKey(), entry.getValue()));
        }

        final CloseableHttpAsyncClient closeableHttpAsyncClient = HttpAsyncClients.createSystem();
        closeableHttpAsyncClient.start();

        HttpHost proxy = new HttpHost(httpsProxyHost, httpsProxyPort);
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();
        httpPost.setConfig(config);

        return closeableHttpAsyncClient.execute(httpPost, new ClosableCallback(closeableHttpAsyncClient));
    }

    @Override
    public PushService setSubject(String subject) {
        super.setSubject(subject);
        this.subject = subject;
        return this;
    }

    @Override
    public PushService setPublicKey(String publicKey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        super.setPublicKey(publicKey);
        this.publicKey = Utils.loadPublicKey(publicKey);
        return this;
    }

    @Override
    public PushService setPrivateKey(String privateKey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        super.setPrivateKey(privateKey);
        this.privateKey = Utils.loadPrivateKey(privateKey);
        return this;
    }

    public void setHttpsProxyHost(String httpsProxyHost) {
        this.httpsProxyHost = httpsProxyHost;
    }

    public void setHttpsProxyPort(int httpsProxyPort) {
        this.httpsProxyPort = httpsProxyPort;
    }
}
