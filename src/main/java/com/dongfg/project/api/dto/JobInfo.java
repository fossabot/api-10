package com.dongfg.project.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 任务信息
 *
 * @author dongfg
 * @date 18-1-29
 */
@Data
@Builder
public class JobInfo {
    private String group;
    private String name;
    private String state;

    private Date prevTime;
    private Date nextTime;
}
