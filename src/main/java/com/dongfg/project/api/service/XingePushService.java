package com.dongfg.project.api.service;

import com.dongfg.project.api.component.XingeComponent;
import com.dongfg.project.api.entity.MessageInfo;
import com.dongfg.project.api.graphql.type.PushResult;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dongfg
 * @date 2018/1/1
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class XingePushService {

    @NonNull
    private XingeComponent xingeComponent;

    public PushResult xingePush(String account, MessageInfo messageInfo) {
        xingeComponent.sendMessage(account, messageInfo);
        return new PushResult(1,0);
    }
}
