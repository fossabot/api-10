package com.dongfg.project.api.service;

import com.dongfg.project.api.config.ApiProperty;
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
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dongfg
 * @date 2017/10/15
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SmsService {
    @NonNull
    private ApiProperty apiProperty;

    @NonNull
    private SmsRepository smsRepository;

    private YunpianClient client;

    @PostConstruct
    public void init() {
        client = new YunpianClient(apiProperty.getYunpianApikey()).init();
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
