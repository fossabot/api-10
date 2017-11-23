package com.dongfg.project.api.graphql.type;

import lombok.Data;

/**
 * @author dongfg
 * @date 17-11-22
 */
@Data
public class SessionResponse {
    private int code;
    private String sessionId;
}
