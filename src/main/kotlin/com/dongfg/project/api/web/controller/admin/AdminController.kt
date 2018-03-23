package com.dongfg.project.api.web.controller.admin

import com.dongfg.project.api.web.payload.GenericPayload
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author dongfg
 * @date 2018/3/23
 */
@RequestMapping("/admin")
@RestController
class AdminController {

    @GetMapping
    fun index(): GenericPayload {
        return GenericPayload(true)
    }
    
}