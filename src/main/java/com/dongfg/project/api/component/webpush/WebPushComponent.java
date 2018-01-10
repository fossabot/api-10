package com.dongfg.project.api.component.webpush;

import com.dongfg.project.api.config.property.ApiProperty;
import com.dongfg.project.api.graphql.type.PushSubscription;
import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.Subscription;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

/**
 * @author dongfg
 * @date 18-1-3
 */
@Component
@Slf4j
public class WebPushComponent {
    private static final String VAPID_PUBLIC_KEY = "BLrIdg4o5HbVvDA7BkNqo4iXUaj0cYr9RU8/MLmPf/czBhfSElMN5LHQKeLlHBPYI77RX2nE0B56UUn92PgnnqY=";

    @Value("${spring.application.name}")
    private String appName;

    @Value("${https.proxyHost:127.0.0.1}")
    private String httpsProxyHost;

    @Value("${https.proxyPort:8118}")
    private int httpsProxyPort;

    @Autowired
    private ApiProperty apiProperty;

    /**
     * 推送web消息
     *
     * @param pushSubscription 推送对象
     * @param payload          推送内容(推荐使用json)
     */
    public void sendMessage(PushSubscription pushSubscription, String payload) {
        Security.addProvider(new BouncyCastleProvider());

        Subscription subscription = new Subscription();
        subscription.endpoint = pushSubscription.getEndpoint();
        subscription.keys = subscription.new Keys(pushSubscription.getP256dh(), pushSubscription.getAuth());

        ProxyPushService pushService = new ProxyPushService();

        Notification notification = null;
        try {
            notification = new Notification(subscription, payload);

            pushService.setSubject(appName);
            pushService.setPublicKey(VAPID_PUBLIC_KEY);
            pushService.setPrivateKey(apiProperty.getPrivateVapid());
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException ignore) {
        }

        pushService.setHttpsProxyHost(httpsProxyHost);
        pushService.setHttpsProxyPort(httpsProxyPort);


        try {
            pushService.sendAsync(notification);
        } catch (Exception e) {
            log.error("failed to push notification", e);
        }

    }
}
