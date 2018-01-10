package com.dongfg.project.api.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author dongfg
 * @date 18-1-9
 */
@Data
@NoArgsConstructor
@Document(collection = "atom_rss")
public class AtomRssInfo {

    @Id
    private String name;
    /**
     * rss 标题
     */
    private String title;

    /**
     * rss url
     */
    private String url;

    /**
     * 最后更新时间
     */
    private Date updateDate;

    /**
     * rss check 时间
     */
    private Date checkDate;

    public AtomRssInfo(String name, String title, String url) {
        this.name = name;
        this.title = title;
        this.url = url;
    }
}
