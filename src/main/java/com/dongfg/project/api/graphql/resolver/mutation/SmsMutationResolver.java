package com.dongfg.project.api.graphql.resolver.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.dongfg.project.api.graphql.type.Sms;
import com.dongfg.project.api.repository.SmsRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsMutationResolver implements GraphQLMutationResolver {

    @NonNull
    private SmsRepository smsRepository;

    public Sms sendSms(String token, Sms input) {
        input.setCreateTime(new Date());
        return smsRepository.save(input);
    }
}
