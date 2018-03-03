package com.dongfg.project.api.controller.admin;

import com.dongfg.project.api.dto.CommonResponse;
import com.dongfg.project.api.dto.JobInfo;
import com.dongfg.project.api.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dongfg
 * @date 18-1-29
 */
@RequestMapping("/admin/task")
@RestController
@Slf4j
public class JobController {
    @Autowired
    private JobService jobService;

    @GetMapping("list")
    public List<JobInfo> list() {
        return jobService.jobInfoList();
    }

    @PostMapping("toggle")
    public CommonResponse<Void> toggle(@RequestParam String group, @RequestParam String name) {
        return jobService.toggleJob(group, name);
    }
}
