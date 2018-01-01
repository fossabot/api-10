package com.dongfg.project.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dongfg
 * @date 18-01-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageInfo {
    private String title;
    private String content;

    private String catalog;
    /**
     * @see com.dongfg.project.api.util.Constants.MessageLevel
     */
    private String level;

    private String time;
}
