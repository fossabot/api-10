package com.dongfg.project.api.graphql.type;

import lombok.Data;

/**
 * @author dongfg
 * @date 17-11-7
 */
@Data
public class PushPayload {
    private String title;
    private String message;
}
