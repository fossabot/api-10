package com.dongfg.project.api.graphql.resolver.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dongfg.project.api.graphql.type.Sms;
import com.dongfg.project.api.repository.SmsRepository;
import com.dongfg.project.api.service.SmsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dongfg
 * @date 2017/10/15
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsQueryResolver implements GraphQLQueryResolver {

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

}
