package com.dongfg.project.api.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dongfg.project.api.graphql.type.Message;
import com.dongfg.project.api.graphql.type.Sms;
import com.dongfg.project.api.service.CommonService;
import com.dongfg.project.api.service.MessageService;
import com.dongfg.project.api.util.RamRateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dongfg
 * @date 17-11-22
 */
@Component
@Slf4j
public class SmsResolver implements GraphQLQueryResolver, GraphQLMutationResolver {
    @Autowired
    private CommonService commonService;

    @Autowired
    private MessageService messageService;

    public Sms sendSms(int otpCode, Sms input) {
        if (!RamRateLimiter.acquire(RamRateLimiter.LIMIT_KEY_SMS)) {
            throw new RuntimeException("access rate limit exceeded");
        }
        if (commonService.invalidOtpCode(otpCode)) {
            throw new RuntimeException("invalid otp code");
        }

        Message message = Message.builder()
                .receiver(input.getMobile())
                .content(input.getContent())
                .build();
        messageService.sendMessage(message);
        return input;
    }
}
