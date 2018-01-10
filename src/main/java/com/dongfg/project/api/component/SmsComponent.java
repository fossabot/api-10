package com.dongfg.project.api.component;

import com.dongfg.project.api.config.property.ApiProperty;
import com.dongfg.project.api.dto.CommonResponse;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;
import com.yunpian.sdk.model.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dongfg
 * @date 2017/10/15
 */
@Component
@Slf4j
public class SmsComponent {

    @Autowired
    private ApiProperty apiProperty;

    private YunpianClient client;

    @PostConstruct
    public void init() {
        client = new YunpianClient(apiProperty.getYunpianApikey()).init();
    }

    public CommonResponse sendMessage(String mobile, String content) {
        Map<String, String> param = client.newParam(2);
        param.put(YunpianClient.MOBILE, mobile);
        param.put(YunpianClient.TEXT, content);
        Result<SmsSingleSend> r = client.sms().single_send(param);
        return CommonResponse.builder()
                .success(r.isSucc())
                .msg(r.getMsg())
                .build();
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
