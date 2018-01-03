package com.dongfg.project.api.graphql.payload;

import lombok.Builder;
import lombok.Data;

/**
 * @author dongfg
 * @date 2018/1/2
 */
@Data
@Builder
public class MessagePayload {
    private boolean success;
    private String msg;
}
