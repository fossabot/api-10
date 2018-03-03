package com.dongfg.project.api.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 微信小程序登录,code换session key
 *
 * @author dongfg
 * @date 2017/11/22
 */
@Data
public class Code2SessionResponse {

    @JSONField(name = "openid")
    private String openId;

    @JSONField(name = "session_key")
    private String sessionKey;

    @JSONField(name = "unionid")
    private String unionId;

    @JSONField(name = "errcode")
    private int errCode;

    @JSONField(name = "errmsg")
    private String errMsg;
}
