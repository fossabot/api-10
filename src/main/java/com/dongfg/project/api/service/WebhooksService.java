package com.dongfg.project.api.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dongfg.project.api.config.property.ApiProperty;
import com.dongfg.project.api.dto.CommonResponse;
import com.dongfg.project.api.graphql.type.Message;
import com.dongfg.project.api.graphql.type.MessageLevel;
import com.dongfg.project.api.graphql.type.MessageType;
import com.dongfg.project.api.util.Constants;
import com.dongfg.project.api.util.DateTimeConverter;
import com.google.common.base.Joiner;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author dongfg
 * @date 18-1-5
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class WebhooksService {

    @NonNull
    private MessageService messageService;

    @NonNull
    private ApiProperty apiProperty;

    /**
     * travis-ci notification process
     *
     * @param payload travis-ci notification
     * @return common response
     */
    public CommonResponse travisci(String payload) {
        // 根据通知级别，发送不同级别的APP通知
        JSONObject payloadJson = JSON.parseObject(payload);
        int status = payloadJson.getInteger("status");
        String statusMessage = payloadJson.getString("status_message");

        String repo = payloadJson.getJSONObject("repository").getString("name");

        String buildUrl = payloadJson.getString("build_url");
        String commitMessage = payloadJson.getString("message");

        MessageLevel level;
        if (status == 0 || Constants.TravisStatus.PENDING.equals(statusMessage)) {
            level = MessageLevel.INFO;
        } else {
            level = MessageLevel.ERROR;
        }

        Message message = Message.builder()
                .receiver(apiProperty.getAppPushAccount())
                .type(MessageType.APP)
                .title(Joiner.on(" ").join("Build", repo, statusMessage))
                .content(String.format(Constants.MessageTemplate.TRAVIS_CI_BUILD, repo, buildUrl, commitMessage))
                .catalog("Travis-CI")
                .level(level)
                .time(DateTimeConverter.formatDate(new Date()))
                .build();

        messageService.sendMessage(message);

        return CommonResponse.builder().success(true).build();
    }
}
