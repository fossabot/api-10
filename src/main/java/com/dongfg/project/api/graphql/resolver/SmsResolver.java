package com.dongfg.project.api.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dongfg.project.api.graphql.type.Sms;
import com.dongfg.project.api.repository.SmsRepository;
import com.dongfg.project.api.service.CommonService;
import com.dongfg.project.api.service.SmsService;
import com.dongfg.project.api.util.RamRateLimiter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dongfg
 * @date 17-11-22
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsResolver implements GraphQLQueryResolver, GraphQLMutationResolver {
    @NonNull
    private CommonService commonService;

    @NonNull
    private SmsRepository smsRepository;

    @NonNull
    private SmsService smsService;

    public List<Sms> getSmsList() {
        return smsRepository.findAll();
    }

    public List<String> getSmsTplList() {
        return smsService.smsTplList();
    }

    public Sms sendSms(int otpCode, Sms input) {
        if (!RamRateLimiter.acquire(RamRateLimiter.LIMIT_KEY_SMS)) {
            throw new RuntimeException("access rate limit exceeded");
        }
        if (!commonService.validateOtpCode(otpCode)) {
            throw new RuntimeException("invalid otp code");
        }
        return smsService.sendSms(input);
    }
}
