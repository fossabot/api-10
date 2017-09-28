package com.dongfg.project.api.service;

import com.yunpian.sdk.YunpianClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsService {
    private YunpianClient client;

    @PostConstruct
    public void init() {
        new YunpianClient("apikey").init();
    }
}
