package com.dongfg.project.api.job;

import com.dongfg.project.api.component.quartz.builder.CronJob;
import com.dongfg.project.api.config.property.ApiProperty;
import com.dongfg.project.api.dto.AtomRssEntry;
import com.dongfg.project.api.entity.AtomRssInfo;
import com.dongfg.project.api.graphql.type.Message;
import com.dongfg.project.api.graphql.type.MessageLevel;
import com.dongfg.project.api.graphql.type.MessageType;
import com.dongfg.project.api.repository.AtomRssRepository;
import com.dongfg.project.api.service.MessageService;
import com.dongfg.project.api.util.Constants;
import com.dongfg.project.api.util.DateTimeConverter;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author dongfg
 * @date 18-1-9
 */
@Component
@Slf4j
public class AtomRssJob extends BaseScheduleJob implements Job {

    @Autowired
    private AtomRssRepository atomRssRepository;

    @Autowired
    private ApiProperty apiProperty;

    @Autowired
    private MessageService messageService;

    @PostConstruct
    public void init() {
        apiProperty.getRssList().forEach(rss -> {
            AtomRssInfo rssInfo = atomRssRepository.findOne(rss.getName());
            if (rssInfo == null) {
                atomRssRepository.save(rss);
            }
        });
    }

    @Override
    CronJob buildJob() {
        return CronJob.builder()
                .group(JobKey.DEFAULT_GROUP)
                .name(AtomRssJob.class.getSimpleName())
                .jobClass(getClass())
                .cronExpression("0 0 10 * * ?")
                .startTime(new Date())
                .build();
    }

    @Override
    List<String> messageTemplate() {
        return null;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        atomRssRepository.findAll().forEach(rssInfo -> {
            List<AtomRssEntry> entryList = fetchLatest(rssInfo);
            Date now = new Date();
            entryList.forEach(entry -> {
                Message message = Message.builder()
                        .receiver(apiProperty.getAppPushAccount())
                        .type(MessageType.APP)
                        .title(rssInfo.getTitle())
                        .notification(entry.getTitle())
                        .content(removeLink(entry.getContent()))
                        .catalog(Constants.MessageCatalog.RSS)
                        .level(MessageLevel.INFO)
                        .time(DateTimeConverter.formatDate(now))
                        .build();
                messageService.sendMessage(message);
            });

            Optional<AtomRssEntry> latestEntry = entryList.stream().max(Comparator.comparing(AtomRssEntry::getUpdateDate));
            if (latestEntry.isPresent()) {
                rssInfo.setUpdateDate(latestEntry.get().getUpdateDate());
            } else {
                rssInfo.setUpdateDate(now);
            }
            rssInfo.setCheckDate(now);

            atomRssRepository.save(rssInfo);
        });
    }

    private List<AtomRssEntry> fetchLatest(AtomRssInfo rssInfo) {
        log.info("fetch rss:{}", rssInfo.getTitle());
        List<AtomRssEntry> entryList = new ArrayList<>();
        try {
            SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(rssInfo.getUrl())));
            if (rssInfo.getUpdateDate() == null && !feed.getEntries().isEmpty()) {
                SyndEntry latestEntry = feed.getEntries().get(0);
                AtomRssEntry entry = AtomRssEntry.builder()
                        .title(latestEntry.getTitle())
                        .link(latestEntry.getLink())
                        .updateDate(latestEntry.getUpdatedDate())
                        .build();
                if (!latestEntry.getContents().isEmpty()) {
                    entry.setContent(latestEntry.getContents().get(0).getValue());
                }
                entryList.add(entry);
            } else if (rssInfo.getUpdateDate() != null && !feed.getEntries().isEmpty()) {
                entryList = feed.getEntries().stream().filter(e1 -> e1.getUpdatedDate().compareTo(rssInfo.getUpdateDate()) > 0)
                        .map(e2 -> {
                            AtomRssEntry entry = AtomRssEntry.builder()
                                    .title(e2.getTitle())
                                    .link(e2.getLink())
                                    .updateDate(e2.getUpdatedDate())
                                    .build();
                            if (!e2.getContents().isEmpty()) {
                                entry.setContent(e2.getContents().get(0).getValue());
                            }
                            return entry;
                        }).collect(Collectors.toList());
            }
        } catch (FeedException | IOException e) {
            log.error("{} rss fetch exception", rssInfo.getTitle());
        }
        return entryList;
    }

    private String removeLink(String changelog) {
        StringBuilder builder = new StringBuilder();
        String[] lines = changelog.split("\n");
        for (String rawString : lines) {
            Pattern pattern = Pattern.compile("^.*(?<link><a.*>(?<text>.*)</a>).*$");
            Matcher matcher = pattern.matcher(rawString);

            if (matcher.matches()) {
                builder.append(rawString.replace(matcher.group("link"), matcher.group("text")));
            } else {
                builder.append(rawString);
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}
