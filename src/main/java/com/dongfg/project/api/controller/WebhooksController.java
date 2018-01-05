package com.dongfg.project.api.controller;

import com.dongfg.project.api.dto.CommonResponse;
import com.dongfg.project.api.service.WebhooksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dongfg
 * @date 18-1-5
 */
@RestController
@RequestMapping("/webhooks")
@Slf4j
public class WebhooksController {

    @Autowired
    private WebhooksService webhooksService;

    @PostMapping(value = "/travisci", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public CommonResponse travisci(@RequestHeader("Travis-Repo-Slug") String repo, String payload) {
        log.info("travisci notification received:\nrepo:{}\npayload:{}", repo, payload);
        return webhooksService.travisci(payload);
    }

}
