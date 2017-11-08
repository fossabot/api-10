package com.dongfg.project.api.service;

import com.alibaba.fastjson.JSON;
import com.dongfg.project.api.graphql.type.PushPayload;
import com.dongfg.project.api.graphql.type.PushSubscription;
import com.dongfg.project.api.util.HttpProxyExecutor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Security;

/**
 * @author dongfg
 * @date 17-11-7
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebPushService {

    private static final String VAPID_PUBLIC_KEY = "BLrIdg4o5HbVvDA7BkNqo4iXUaj0cYr9RU8/MLmPf/czBhfSElMN5LHQKeLlHBPYI77RX2nE0B56UUn92PgnnqY=";

    @Value("${vapid.private:null}")
    private String vapidPrivateKey;

    @Value("${spring.application.name}")
    private String appName;

    @NonNull
    private HttpProxyExecutor httpProxyExecutor;

    /**
     * 推送web消息
     *
     * @param pushSubscription 推送对象
     * @param pushPayload      推送内容
     */
    public void sendMessage(PushSubscription pushSubscription, PushPayload pushPayload) throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());

        Subscription subscription = new Subscription();
        subscription.endpoint = pushSubscription.getEndpoint();
        subscription.keys = subscription.new Keys(pushSubscription.getP256dh(), pushSubscription.getAuth());

        Notification notification = new Notification(subscription, JSON.toJSONString(pushPayload));

        PushService pushService = new PushService();
        pushService.setSubject(appName);
        pushService.setPublicKey(VAPID_PUBLIC_KEY);
        pushService.setPrivateKey(vapidPrivateKey);

        httpProxyExecutor.executeWithProxy(() -> {
            try {
                // set https proxy property
                HttpResponse httpResponse = pushService.send(notification);
                log.info(IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8.name()));
            } catch (Exception e) {
                log.error("failed to push notification", e);
            }
        });

    }

}
