package com.dongfg.project.api.service;

import com.alibaba.fastjson.JSON;
import com.dongfg.project.api.component.SmsComponent;
import com.dongfg.project.api.component.XingeComponent;
import com.dongfg.project.api.component.webpush.WebPushComponent;
import com.dongfg.project.api.dto.CommonResponse;
import com.dongfg.project.api.graphql.payload.MessagePayload;
import com.dongfg.project.api.graphql.type.Message;
import com.dongfg.project.api.graphql.type.PushSubscription;
import com.dongfg.project.api.repository.PushSubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dongfg
 * @date 18-1-3
 */
@Service
@Slf4j
public class MessageService {

    @Autowired
    private PushSubscriptionRepository pushSubscriptionRepository;

    @Autowired
    private SmsComponent smsComponent;

    @Autowired
    private WebPushComponent webPushComponent;

    @Autowired
    private XingeComponent xingeComponent;


    public MessagePayload sendMessage(Message input) {
        MessagePayload messagePayload = MessagePayload.builder().success(true).build();
        CommonResponse commonResponse = null;
        switch (input.getType()) {
            case APP:
                commonResponse = xingeComponent.sendMessage(input);
                messagePayload.setSuccess(commonResponse.isSuccess());
                messagePayload.setMsg(commonResponse.getMsg());
                break;
            case SMS:
                commonResponse = smsComponent.sendMessage(input.getReceiver(), input.getContent());
                messagePayload.setSuccess(commonResponse.isSuccess());
                messagePayload.setMsg(commonResponse.getMsg());
                break;
            case WEB:
                pushSubscriptionRepository.findAll().forEach(subscription ->
                        webPushComponent.sendMessage(subscription, JSON.toJSONString(input)));
                break;
            default:
        }

        return messagePayload;
    }

    public MessagePayload savePushSubscription(PushSubscription subscription) {
        pushSubscriptionRepository.save(subscription);
        return MessagePayload.builder().success(true).build();
    }
}
