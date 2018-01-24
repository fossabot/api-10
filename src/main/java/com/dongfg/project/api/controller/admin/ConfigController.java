package com.dongfg.project.api.controller.admin;

import com.dongfg.project.api.dto.CommonResponse;
import com.dongfg.project.api.dto.ConfigRequest;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.OperationException;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

/**
 * 配置管理
 *
 * @author dongfg
 * @date 18-1-24
 */
@RequestMapping("/admin/config")
@RestController
@Slf4j
public class ConfigController {

    @Autowired
    private ConsulClient consulClient;

    @Value("${spring.cloud.consul.config.acl-token}")
    private String token;

    @GetMapping
    public CommonResponse getConfig() {
        CommonResponse<String> commonResponse = CommonResponse.<String>builder().success(true).build();
        Response<GetValue> response = consulClient.getKVValue("config/dongfg-api/data", token);
        if (response.getValue() != null) {
            String base64 = response.getValue().getValue();
            log.info("getConfig {} ", base64);
            String rawString = new String(Base64Utils.decodeFromString(base64));
            commonResponse.setData(rawString);
        }
        return commonResponse;
    }

    @PutMapping
    public CommonResponse updateConfig(@RequestBody ConfigRequest request) {
        CommonResponse<String> commonResponse = CommonResponse.<String>builder().success(true).build();
        try {
            consulClient.setKVValue("config/dongfg-api/data", request.getData(), token, null);
        } catch (OperationException e) {
            commonResponse.setSuccess(false);
            commonResponse.setMsg(e.getStatusMessage());
        }
        return commonResponse;
    }
}
