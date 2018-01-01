package com.dongfg.project.api.component;

import com.dongfg.project.api.config.property.ApiProperty;
import com.dongfg.project.api.entity.MessageInfo;
import com.tencent.xinge.Message;
import com.tencent.xinge.Style;
import com.tencent.xinge.XingeApp;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 信鸽推送组件
 *
 * @author dongfg
 * @date 2018/1/1
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class XingeComponent {

    @NonNull
    private ApiProperty apiProperty;

    private XingeApp xingeApp;

    @PostConstruct
    public void init() {
        xingeApp = new XingeApp(apiProperty.getXingeAccessId(), apiProperty.getXingeSecret());
    }

    public void sendMessage(String account, MessageInfo messageInfo) {
        Message message = new Message();
        message.setType(Message.TYPE_NOTIFICATION);
        message.setTitle(messageInfo.getTitle());
        message.setContent(messageInfo.getContent());
        message.setCustom(buildCustom(messageInfo));
        message.setStyle(new Style(3, 1, 0, 1, 0));
        JSONObject pushResult = xingeApp.pushSingleAccount(0, account, message);
        log.info("XingeComponent#sendMessage:{}",pushResult.toString());
    }

    private Map<String, Object> buildCustom(MessageInfo messageInfo) {
        Map<String, Object> custom = new HashMap<>();
        custom.put("title", messageInfo.getTitle());
        custom.put("content", messageInfo.getContent());
        custom.put("catalog", messageInfo.getCatalog());
        custom.put("level", messageInfo.getLevel());
        custom.put("time", messageInfo.getTime());
        return custom;
    }
}
