package com.dongfg.project.api.graphql.type;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * 评论
 */
@Data
public class Comment {
    @Id
    private String id;
    private String comment;
    private String name;
    private String email;
    private Date createTime;
}
