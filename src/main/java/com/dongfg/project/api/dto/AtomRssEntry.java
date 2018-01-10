package com.dongfg.project.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author dongfg
 * @date 18-1-9
 */
@Data
@Builder
public class AtomRssEntry {
    private String link;
    private String title;
    private String content;
    private Date updateDate;
}
