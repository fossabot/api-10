package com.dongfg.project.api.graphql.type;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class SSLCertificate {
    private String domain;
    private Date expireAt;
}
