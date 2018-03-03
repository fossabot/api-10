package com.dongfg.project.api.controller.admin;

import com.dongfg.project.api.component.SmsComponent;
import com.dongfg.project.api.config.property.ApiProperty;
import com.dongfg.project.api.dto.CommonResponse;
import com.dongfg.project.api.graphql.payload.MessagePayload;
import com.dongfg.project.api.graphql.type.Message;
import com.dongfg.project.api.graphql.type.MessageType;
import com.dongfg.project.api.service.MessageService;
import com.dongfg.project.api.util.DateTimeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author dongfg
 * @date 18-1-29
 */
@RequestMapping("/admin/message")
@RestController
@Slf4j
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private SmsComponent smsComponent;

    @Autowired
    private ApiProperty apiProperty;

    @PostMapping("app")
    public CommonResponse sendAppMessage(@RequestBody Message input) {
        input.setReceiver(apiProperty.getAppPushAccount());
        input.setType(MessageType.APP);
        input.setTime(DateTimeConverter.formatDate(new Date()));
        MessagePayload messagePayload = messageService.sendMessage(input);

        return CommonResponse.builder()
                .success(messagePayload.isSuccess())
                .msg(messagePayload.getMsg()).build();
    }

    @PostMapping("sms")
    public CommonResponse sendSms(@RequestBody Message input) {
        input.setType(MessageType.SMS);
        input.setTime(DateTimeConverter.formatDate(new Date()));
        MessagePayload messagePayload = messageService.sendMessage(input);

        return CommonResponse.builder()
                .success(messagePayload.isSuccess())
                .msg(messagePayload.getMsg()).build();
    }

    @GetMapping("sms/tpl")
    public CommonResponse<List<String>> getSmsTpl() {
        return CommonResponse.<List<String>>builder()
                .success(true)
                .data(smsComponent.smsTplList())
                .build();
    }
}
