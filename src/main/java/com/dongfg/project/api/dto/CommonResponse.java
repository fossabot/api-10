package com.dongfg.project.api.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author dongfg
 * @date 2018/1/2
 */
@Data
@Builder
public class CommonResponse {
    private boolean success;
    private String msg;
}
