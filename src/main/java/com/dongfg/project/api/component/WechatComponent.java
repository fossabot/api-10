package com.dongfg.project.api.component;

import com.dongfg.project.api.config.property.ApiProperty;
import com.dongfg.project.api.dto.Code2SessionResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * @author dongfg
 * @date 2017/11/22
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class WechatComponent {

    @NonNull
    private RestTemplate restTemplate;

    @NonNull
    private ApiProperty apiProperty;

    /**
     * 微信小程序登录,code换session key
     *
     * @param code js code
     * @return session key
     */
    public Optional<Code2SessionResponse> code2Session(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={APPID}&secret={SECRET}&js_code={JSCODE}&grant_type=authorization_code";
        Code2SessionResponse response = null;
        try {
            response = restTemplate.getForObject(url, Code2SessionResponse.class, apiProperty.getWechatAppId()
                    , apiProperty.getWechatAppSecret(), code);
        } catch (RestClientException ignore) {
            log.error("WechatComponent#code2Session", ignore);
        }

        return Optional.ofNullable(response);
    }

}
