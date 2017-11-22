package com.dongfg.project.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 微信小程序登录,code换session key
 *
 * @author dongfg
 * @date 2017/11/22
 */
@Data
public class Code2SessionResponse {

    @JsonProperty("openid")
    private String openId;

    @JsonProperty("session_key")
    private String sessionKey;

    @JsonProperty("unionid")
    private String unionId;

    @JsonProperty("errcode")
    private int errCode;

    @JsonProperty("errmsg")
    private String errMsg;
}
