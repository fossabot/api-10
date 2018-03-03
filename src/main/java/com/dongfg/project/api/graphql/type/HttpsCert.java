package com.dongfg.project.api.graphql.type;

import lombok.Data;

import java.util.Date;

/**
 * https证书信息
 *
 * @author dongfg
 * @date 18-1-25
 */
@Data
public class HttpsCert {
    private String domain;
    private Date issueDate;
    private Date expireDate;
    private long expireDay = -1;
}
