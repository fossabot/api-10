package com.dongfg.project.api.component

import com.dongfg.project.api.common.config.ApiProperty
import com.yunpian.sdk.YunpianClient
import com.yunpian.sdk.model.Result
import com.yunpian.sdk.model.SmsSingleSend
import com.yunpian.sdk.model.Template
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.stream.Collectors
import javax.annotation.PostConstruct

/**
 * @author dongfg
 * @date 2018/3/16
 */
@Component
class Sms {

    @Autowired
    private lateinit var apiProperty: ApiProperty

    lateinit var client: YunpianClient

    @PostConstruct
    fun init() {
        client = YunpianClient(apiProperty.yunpianApikey).init()
    }

    fun templates(): List<String> {
        val result = client.tpl().get(emptyMap())
        return result.data.stream().filter({ tpl ->
            "SUCCESS" == tpl.check_status
        }).map(Template::getTpl_content).collect(Collectors.toList())
    }

    fun send(mobile: String, message: String): Result<SmsSingleSend> {
        val param = client.newParam(2)
        param[YunpianClient.MOBILE] = mobile
        param[YunpianClient.TEXT] = message

        return client.sms().single_send(param)
    }
}