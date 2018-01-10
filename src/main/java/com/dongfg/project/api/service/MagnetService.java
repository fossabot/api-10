package com.dongfg.project.api.service;

import com.dongfg.project.api.graphql.type.Magnet;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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
@Slf4j
public class MagnetService {

    private static final int MAX_SIZE = 5 * 1024;
    private static final String SIZE_UNIT_BG = "GB";

    @Cacheable("searchByKeyWords")
    public Magnet searchByKeyWords(String keyWords) {
        Magnet magnet = null;


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
            List<Magnet> magnetList = new ArrayList<>();
            resultItems.stream().limit(5).forEach(item -> {
                Magnet info = new Magnet();
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

                magnetList.add(info);
            });
            magnet = choose(magnetList);
            magnet.setKeyWords(keyWords);
        } catch (IOException e) {
            log.error("Jsoup.connect exception", e);
        }
        return magnet;
    }

    private Magnet choose(List<Magnet> magnetList) {
        if (magnetList.isEmpty()) {
            return new Magnet();
        }

        magnetList.forEach(i -> {
            if (i.getSize().contains(SIZE_UNIT_BG)) {
                i.setSize("" + Double.parseDouble(i.getSize().split(" ")[0]) * 1000);
            } else {
                i.setSize("" + Double.parseDouble(i.getSize().split(" ")[0]));
            }
        });

        Optional<Magnet> result = magnetList.stream().sorted(Comparator.comparing(info -> Double.valueOf(info.getSize()), Comparator.reverseOrder())).
                filter(magnet -> {
                    boolean filter = true;
                    // exclude iso format
                    for (String f : magnet.getFiles()) {
                        if (f.endsWith(".iso") || f.endsWith(".ISO")) {
                            filter = false;
                            break;
                        }
                    }
                    // exclude size too big
                    if (Double.valueOf(magnet.getSize()) > MAX_SIZE) {
                        filter = false;
                    }
                    return filter;
                }).findFirst();
        return result.orElse(new Magnet());
    }
}
