package com.dongfg.project.api.graphql.type;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author dongfg
 * @date 17-11-7
 */
@Data
@AllArgsConstructor
public class PushResult {
    private int successNum;
    private int failNum;
}
