package com.dongfg.project.api.config;

import com.dongfg.project.api.graphql.type.Message;
import com.dongfg.project.api.graphql.type.MessageLevel;
import com.dongfg.project.api.graphql.type.MessageType;
import com.dongfg.project.api.service.MessageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

/**
 * @author dongfg
 * @date 17-11-9
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Profile("prd")
public class PublishConfig {

    @NonNull
    private MessageService messageService;

    @EventListener(ApplicationReadyEvent.class)
    public void sendPublishMessage() {
        Message message = new Message();
        message.setType(MessageType.WEB);
        message.setLevel(MessageLevel.INFO);
        message.setTitle("网站通知");
        message.setContent("新版本发布完成");

        messageService.sendMessage(message);
    }
}
