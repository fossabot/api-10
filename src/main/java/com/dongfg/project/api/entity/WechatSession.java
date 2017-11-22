package com.dongfg.project.api.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author dongfg
 * @date 17-11-22
 */
@Document(collection = "wechat_session")
@Data
@Builder
public class WechatSession {

    @Id
    private String id;

    /**
     * 服务器生成的sessionId
     */
    private String sessionId;

    /**
     * 微信用户ID
     */
    private String openId;

    /**
     * 微信登录返回的session_key
     */
    private String sessionKey;
}
