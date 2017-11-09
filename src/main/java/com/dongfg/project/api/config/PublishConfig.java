package com.dongfg.project.api.config;

import com.dongfg.project.api.graphql.type.PushPayload;
import com.dongfg.project.api.repository.PushSubscriptionRepository;
import com.dongfg.project.api.service.WebPushService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.security.GeneralSecurityException;

/**
 * @author dongfg
 * @date 17-11-9
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PublishConfig {

    @NonNull
    private WebPushService webPushService;

    @NonNull
    private PushSubscriptionRepository pushSubscriptionRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void sendPublishMessage() {
        PushPayload pushPayload = new PushPayload();
        pushPayload.setTitle("网站通知");
        pushPayload.setMessage("新版本发布完成");
        pushSubscriptionRepository.findAll().forEach(subscription -> {
            try {
                webPushService.sendMessage(subscription, pushPayload);
            } catch (GeneralSecurityException e) {
                log.error("failed to load publicKey", e);
            }
        });
    }

}
