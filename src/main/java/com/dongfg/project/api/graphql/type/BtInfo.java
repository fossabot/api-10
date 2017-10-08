package com.dongfg.project.api.graphql.type;

import lombok.Data;

import java.util.List;

@Data
public class BtInfo {
    private String keyWords;
    private String magnet;

    private String title;
    private String infoUrl;
    private List<String> files;
    private String size;
}
