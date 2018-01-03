package com.dongfg.project.api.graphql.payload;

import lombok.Data;

/**
 * @author dongfg
 * @date 17-11-22
 */
@Data
public class SessionPayload {
    private int code;
    private String sessionId;
}