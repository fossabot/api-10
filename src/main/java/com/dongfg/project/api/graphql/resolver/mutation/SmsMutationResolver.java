package com.dongfg.project.api.graphql.resolver.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.dongfg.project.api.graphql.type.Sms;
import com.dongfg.project.api.service.CommonService;
import com.dongfg.project.api.service.SmsService;
import com.dongfg.project.api.util.RamRateLimiter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dongfg
 * @date 2017/10/15
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsMutationResolver implements GraphQLMutationResolver {

    @NonNull
    private SmsService smsService;

    @NonNull
    private CommonService commonService;

    public Sms sendSms(String token, Sms input) {
        if (!RamRateLimiter.acquire(RamRateLimiter.LIMIT_KEY_SMS)) {
            throw new RuntimeException("access rate limit exceeded");
        }
        if (!commonService.validateToken(token)) {
            throw new RuntimeException("invalid token");
        }
        return smsService.sendSms(input);
    }
}
