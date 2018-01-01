package com.dongfg.project.api.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dongfg.project.api.entity.MessageInfo;
import com.dongfg.project.api.graphql.type.PushPayload;
import com.dongfg.project.api.graphql.type.PushResult;
import com.dongfg.project.api.graphql.type.PushSubscription;
import com.dongfg.project.api.repository.PushSubscriptionRepository;
import com.dongfg.project.api.service.CommonService;
import com.dongfg.project.api.service.WebPushService;
import com.dongfg.project.api.service.XingePushService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;

/**
 * @author dongfg
 * @date 18-1-7
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class XingePushResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @NonNull
    private CommonService commonService;

    @NonNull
    private XingePushService xingePushService;

    public PushResult xingePush(int otpCode, String account, MessageInfo messageInfo) {
        if (!commonService.validateOtpCode(otpCode)) {
            throw new RuntimeException("invalid otp code");
        }
        return xingePushService.xingePush(account, messageInfo);
    }
}
