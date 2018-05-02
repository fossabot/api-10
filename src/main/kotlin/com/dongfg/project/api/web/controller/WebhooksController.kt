package com.dongfg.project.api.web.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/webhooks")
@RestController
class WebhooksController {
    @PostMapping(value = ["/travisci"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun travisci(@RequestHeader("Travis-Repo-Slug") repo: String, payload: String) {

    }
}