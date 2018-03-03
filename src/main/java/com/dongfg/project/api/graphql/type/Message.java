package com.dongfg.project.api.graphql.type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dongfg
 * @date 18-1-2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String receiver;
    private MessageType type;
    private String title;
    /**
     * 通知栏消息
     */
    private String notification;
    private String content;
    private String catalog;
    private MessageLevel level;
    private String time;
}
