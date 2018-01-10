package com.dongfg.project.api;

import com.dongfg.project.api.util.DateTimeConverter;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.junit.Test;

import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dongfg
 * @date 18-1-9
 */
public class AtomRssTest {

    @Test
    public void testParse() throws Exception {
        String url = "https://github.com/alibaba/fastjson/releases.atom";
        SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(url)));
        System.out.println(feed.getTitle());
        System.out.println(DateTimeConverter.formatDate(feed.getPublishedDate()));

        List<SyndEntry> entries = feed.getEntries();
        entries.forEach(e -> {
            System.out.println(DateTimeConverter.formatDate(e.getUpdatedDate()));
            System.out.println(e.getContents().get(0).getValue());
        });
    }

    @Test
    public void removeLink() {
        String rawString = "<li>修复使用NameFilter存在时WriteNullBooleanAsFalse不起作用的问题 <a href=\"https://github.com/alibaba/fastjson/issues/1635\" class=\"issue-link js-issue-link\" data-error-text=\"Failed to load issue title\" data-id=\"280090354\" data-permission-text=\"Issue title is private\" data-url=\"https://github.com/alibaba/fastjson/issues/1635\">#1635</a></li>";

        Pattern pattern = Pattern.compile("^.*(?<link><a.*>(?<text>.*)</a>).*$");
        Matcher matcher = pattern.matcher(rawString);

        System.out.println(matcher.matches());

        System.out.println(matcher.groupCount());

        System.out.println(matcher.group("link"));
        System.out.println(matcher.group("text"));

        System.out.println(rawString.replace(matcher.group("link"), matcher.group("text")));
    }

}
