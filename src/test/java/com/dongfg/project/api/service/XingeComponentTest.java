package com.dongfg.project.api.service;

import com.dongfg.project.api.component.XingeComponent;
import com.dongfg.project.api.config.property.ApiProperty;
import com.dongfg.project.api.entity.MessageInfo;
import com.dongfg.project.api.util.Constants;
import com.tencent.xinge.Message;
import com.tencent.xinge.XingeApp;
import lombok.NonNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dongfg
 * @date 2018/1/1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class XingeComponentTest {

    @Autowired
    private XingeComponent xingeComponent;

    @Test
    public void contentLoad() {
        xingeComponent.sendMessage("Master", MessageInfo.builder()
                .title("推送测试4")
                .content("服务端推送测试4")
                .catalog("推送测试")
                .level(Constants.MessageLevel.INFO)
                .time("2018-01-01 19:57:00").build());
    }

}
