package com.dongfg.project.api.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dongfg.project.api.graphql.type.PushPayload;
import com.dongfg.project.api.graphql.type.PushResult;
import com.dongfg.project.api.graphql.type.PushSubscription;
import com.dongfg.project.api.repository.PushSubscriptionRepository;
import com.dongfg.project.api.service.WebPushService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;

/**
 * @author dongfg
 * @date 17-11-7
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebPushResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @NonNull
    private WebPushService webPushService;

    @NonNull
    private PushSubscriptionRepository pushSubscriptionRepository;

    public String savePushSubscription(PushSubscription subscription) {
        pushSubscriptionRepository.save(subscription);
        return "SUCCESS";
    }

    public PushResult webPush(PushPayload pushPayload) {
        PushResult pushResult = new PushResult(0, 0);

        pushSubscriptionRepository.findAll().forEach(subscription -> {
            try {
                log.info(subscription.getEndpoint());
                log.info(subscription.getP256dh());
                log.info(subscription.getAuth());
                webPushService.sendMessage(subscription, pushPayload);
                pushResult.setSuccessNum(pushResult.getSuccessNum() + 1);
            } catch (GeneralSecurityException e) {
                log.error("failed to load publicKey", e);
                pushResult.setFailNum(pushResult.getFailNum() + 1);
            }
        });

        return pushResult;
    }
}
