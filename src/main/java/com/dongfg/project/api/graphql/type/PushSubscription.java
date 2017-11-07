package com.dongfg.project.api.graphql.type;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author dongfg
 * @date 17-11-7
 */
@Data
@Builder
public class PushSubscription {
    @Id
    private String endpoint;
    private String p256dh;
    private String auth;
}
