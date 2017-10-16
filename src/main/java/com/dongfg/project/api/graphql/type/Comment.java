package com.dongfg.project.api.graphql.type;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author dongfg
 * @date 2017/10/15
 */
@Data
public class Comment {
    @Id
    private String id;
    private String comment;
    private String name;
    private String email;
    private String clientIp;
    private Date createTime;
}
