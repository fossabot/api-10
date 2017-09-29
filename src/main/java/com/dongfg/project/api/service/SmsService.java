package com.dongfg.project.api.service;

import com.dongfg.project.api.graphql.type.Sms;
import com.dongfg.project.api.repository.SmsRepository;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;
import com.yunpian.sdk.model.Template;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SmsService {

    @Value("${yunpian.apikey:null}")
    private String apikey;

    private YunpianClient client;

    @NonNull
    private SmsRepository smsRepository;

    @PostConstruct
    public void init() {
        client = new YunpianClient(apikey).init();
    }

    public Sms sendSms(Sms input) {
        Map<String, String> param = client.newParam(2);
        param.put(YunpianClient.MOBILE, input.getMobile());
        param.put(YunpianClient.TEXT, input.getContent());
        Result<SmsSingleSend> r = client.sms().single_send(param);

        input.setCreateTime(new Date());
        input.setResult(r.getMsg());
        return smsRepository.save(input);
    }

    public List<String> smsTplList() {
        Map<String, String> param = client.newParam(1);
        Result<List<Template>> r = client.tpl().get(param);
        return r.getData().stream()
                .filter(t -> "SUCCESS".equals(t.getCheck_status()))
                .map(Template::getTpl_content)
                .collect(Collectors.toList());
    }
}
