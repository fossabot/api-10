package com.dongfg.project.api.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dongfg.project.api.graphql.payload.MessagePayload;
import com.dongfg.project.api.graphql.type.Message;
import com.dongfg.project.api.graphql.type.PushSubscription;
import com.dongfg.project.api.service.CommonService;
import com.dongfg.project.api.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 统一消息发送
 *
 * @author dongfg
 * @date 18-1-2
 */
@Component
@Slf4j
public class MessageResolver implements GraphQLQueryResolver, GraphQLMutationResolver {
    @Autowired
    private CommonService commonService;

    @Autowired
    private MessageService messageService;

    /**
     * 浏览器通知订阅(允许)
     *
     * @param subscription web browser subscription
     * @return result
     */
    public MessagePayload savePushSubscription(PushSubscription subscription) {
        return messageService.savePushSubscription(subscription);
    }

    public MessagePayload sendMessage(int totpCode, Message input) {
        if (commonService.invalidOtpCode(totpCode)) {
            throw new RuntimeException("invalid otp code");
        }

        return messageService.sendMessage(input);
    }

}
