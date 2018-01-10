package com.dongfg.project.api.service;

import com.dongfg.project.api.component.XingeComponent;
import com.dongfg.project.api.dto.CommonResponse;
import com.dongfg.project.api.graphql.payload.MessagePayload;
import com.dongfg.project.api.graphql.type.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dongfg
 * @date 2018/1/1
 */
@Service
@Slf4j
public class AppMsgService {

    @Autowired
    private XingeComponent xingeComponent;

    public MessagePayload sendMessage(Message message) {
        CommonResponse commonResponse = xingeComponent.sendMessage(message);
        return MessagePayload.builder()
                .success(commonResponse.isSuccess())
                .msg(commonResponse.getMsg())
                .build();
    }
}
