package com.dongfg.project.api.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author dongfg
 * @date 17-11-22
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "api")
public class ApiProperty {
    /**
     * 云片短信发送
     */
    private String yunpianApikey;

    /**
     * chrome消息推送
     */
    private String privateVapid;

    /**
     * 两步验证KEY
     */
    private String otpSecret;

    /**
     * 小程序ID
     */
    private String wechatAppId;

    /**
     * 小程序密钥
     */
    private String wechatAppSecret;

    /**
     * 信鸽推送应用
     */
    private long xingeAccessId;

    /**
     * 信鸽推送密钥
     */
    private String xingeSecret;

    /**
     * app消息推送的用户
     */
    private String appPushAccount;
}
