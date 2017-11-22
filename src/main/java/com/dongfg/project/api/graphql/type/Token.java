package com.dongfg.project.api.graphql.type;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 两步验证token基本信息
 *
 * @author dongfg
 * @date 17-11-22
 */
@Data
@Builder
@Document(collection = "token")
public class Token {
    @Id
    private String id;
    private String openId;
    private String appName;
    private String userName;
    private String secretKey;
}
