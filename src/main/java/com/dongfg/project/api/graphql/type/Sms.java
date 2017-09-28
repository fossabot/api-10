package com.dongfg.project.api.graphql.type;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Sms {
    @Id
    private String id;
    private String mobile;
    private String content;
    private Date createTime;
}
