package com.dongfg.project.api.service;

import com.dongfg.project.api.graphql.type.BtInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author dongfg
 * @date 2017/10/15
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class BtService {

    private static final int MAX_SIZE = 5 * 1024;
    private static final String SIZE_UNIT_BG = "GB";

    @Cacheable("btSearch")
    public BtInfo btSearch(String keyWords) {
        BtInfo btInfo = null;


        String encodeKeyWords = null;
        try {
            encodeKeyWords = URLEncoder.encode(keyWords, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            // ignore
        }

        String url = String.format("http://btdb.to/q/%s/", encodeKeyWords);
        try {
            Document doc = Jsoup.connect(url).get();
            Elements resultItems = doc.select(".search-ret-item");
            List<BtInfo> btInfoList = new ArrayList<>();
            resultItems.stream().limit(5).forEach(item -> {
                BtInfo info = new BtInfo();
                info.setTitle(item.select(".item-title > a").first().attr("title"));
                info.setInfoUrl("http://btdb.to" + item.select(".item-title > a").first().attr("href"));
                info.setMagnet(item.select(".item-meta-info > a").first().attr("href"));
                info.setSize(item.select(".item-meta-info-value").first().text());

                // fetch from infoUrl
                try {
                    Document btInfoDetail = Jsoup.connect(info.getInfoUrl()).get();
                    Elements fileListTable = btInfoDetail.select(".torrent-file-list");
                    Elements fileListElements = fileListTable.select("tr");
                    info.setFiles(fileListElements.stream().map(tr -> tr.select("td").get(1).text()).collect(Collectors.toList()));
                } catch (IOException e) {
                    // ignore
                }

                btInfoList.add(info);
            });
            btInfo = choose(btInfoList);
            btInfo.setKeyWords(keyWords);
        } catch (IOException e) {
            log.error("Jsoup.connect exception", e);
        }
        return btInfo;
    }

    private BtInfo choose(List<BtInfo> btInfoList) {
        if (btInfoList.isEmpty()) {
            return new BtInfo();
        }

        btInfoList.forEach(i -> {
            if (i.getSize().contains(SIZE_UNIT_BG)) {
                i.setSize("" + Double.parseDouble(i.getSize().split(" ")[0]) * 1000);
            } else {
                i.setSize("" + Double.parseDouble(i.getSize().split(" ")[0]));
            }
        });

        Optional<BtInfo> result = btInfoList.stream().sorted(Comparator.comparing(info -> Double.valueOf(info.getSize()), Comparator.reverseOrder())).
                filter(btInfo -> {
                    boolean filter = true;
                    // exclude iso format
                    for (String f : btInfo.getFiles()) {
                        if (f.endsWith(".iso") || f.endsWith(".ISO")) {
                            filter = false;
                            break;
                        }
                    }
                    // exclude size too big
                    if (Double.valueOf(btInfo.getSize()) > MAX_SIZE) {
                        filter = false;
                    }
                    return filter;
                }).findFirst();
        return result.orElse(new BtInfo());
    }
}
