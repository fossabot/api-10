package com.dongfg.project.api.controller.admin;

import com.dongfg.project.api.dto.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dongfg
 * @date 18-1-24
 */
@RequestMapping("/admin")
@RestController
public class AdminController {

    @GetMapping
    public CommonResponse<String> index() {
        return CommonResponse.<String>builder().success(true).build();
    }
}
