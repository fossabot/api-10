package com.dongfg.project.api.component;

import com.dongfg.project.api.config.property.ApiProperty;
import com.dongfg.project.api.dto.CommonResponse;
import com.dongfg.project.api.graphql.type.Message;
import com.tencent.xinge.Style;
import com.tencent.xinge.XingeApp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Slf4j
public class XingeComponent {

    @Autowired
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
        if (StringUtils.isNotEmpty(input.getNotification())) {
            message.setContent(input.getNotification());
        }
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
        custom.put("level", message.getLevel().name());
        custom.put("time", message.getTime());
        return custom;
    }

    private static String cutString(String source, int len) {
        byte[] byteArr = source.getBytes();
        if (byteArr.length < len) {
            return source;
        }
        int count = 0;
        // 统计要截取的那部分字节中负数的个数
        for (int i = 0; i < len; i++) {
            if (byteArr[i] < 0) {
                count++;
            }
        }
        // 负数成对出现 则不会出现半个汉字
        if (count % 2 == 0)
            return new String(byteArr, 0, len);
            // 负数个数不是偶数，则有半个汉字
        else
            return new String(byteArr, 0, len - 1);
    }
}
