package com.dongfg.project.api.graphql.type;

import lombok.Data;

/**
 * 两步验证token基本信息
 *
 * @author dongfg
 * @date 17-11-22
 */
@Data
public class Token {
    private String id;
    private String openId;
    private String appName;
    private String userName;
    private String secretKey;
}
