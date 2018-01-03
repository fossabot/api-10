package com.dongfg.project.api.component;

import com.dongfg.project.api.config.property.ApiProperty;
import com.dongfg.project.api.dto.CommonResponse;
import com.dongfg.project.api.graphql.type.Message;
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

    public CommonResponse sendMessage(Message input) {
        com.tencent.xinge.Message message = new com.tencent.xinge.Message();
        message.setType(com.tencent.xinge.Message.TYPE_NOTIFICATION);
        message.setTitle(input.getTitle());
        message.setContent(input.getContent());
        message.setCustom(buildCustom(input));
        message.setStyle(new Style(3, 1, 0, 1, 0));
        JSONObject pushResult = xingeApp.pushSingleAccount(0, input.getReceiver(), message);
        log.info("XingeComponent#sendMessage:{}", pushResult.toString());
        return CommonResponse.builder()
                .success(pushResult.getInt("ret_code") == 0)
                .msg(pushResult.optString("err_msg", null))
                .build();
    }

    private Map<String, Object> buildCustom(Message message) {
        Map<String, Object> custom = new HashMap<>(5);
        custom.put("title", message.getTitle());
        custom.put("content", message.getContent());
        custom.put("catalog", message.getCatalog());
        custom.put("level", message.getLevel());
        custom.put("time", message.getTime());
        return custom;
    }
}
