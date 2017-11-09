package com.dongfg.project.api.graphql.type;

import lombok.Builder;
import lombok.Data;

/**
 * @author dongfg
 * @date 17-11-9
 */
@Data
@Builder
public class SecretKey {
    private String secret;
    private String qrCodeUrl;
}
