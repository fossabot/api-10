package com.dongfg.project.api.graphql.type;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Builder
public class Token {
    @Id
    private String token;
    private boolean enabled;
    private int requestTimes;
    private Date lastRequestTime;
}
