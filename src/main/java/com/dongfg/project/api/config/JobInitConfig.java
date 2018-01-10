package com.dongfg.project.api.config;

import com.dongfg.project.api.job.BaseScheduleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author dongfg
 * @date 18-1-3
 */
@Configuration
public class JobInitConfig {
    @Autowired(required = false)
    private List<BaseScheduleJob> jobs;

    @EventListener(ApplicationReadyEvent.class)
    public void initJob() {
        if (CollectionUtils.isEmpty(jobs)) {
            return;
        }
        jobs.forEach(BaseScheduleJob::submitJob);
    }
}
