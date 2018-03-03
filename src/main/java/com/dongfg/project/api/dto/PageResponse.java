package com.dongfg.project.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @param <T> data type of page list
 * @author dongfg
 * @date 2018/02/11
 */
@Data
@Builder
public class PageResponse<T> {
    private boolean success;
    private String msg;
    private int currPage;
    private int pageSize;

    private List<T> dataList;
}
