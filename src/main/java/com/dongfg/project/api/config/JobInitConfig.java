package com.dongfg.project.api.config;

import com.dongfg.project.api.job.BaseScheduleJob;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.List;

/**
 * @author dongfg
 * @date 18-1-3
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JobInitConfig {
    @NonNull
    private List<BaseScheduleJob> jobs;

    @EventListener(ApplicationReadyEvent.class)
    public void initJob() {
        jobs.forEach(BaseScheduleJob::submitJob);
    }
}
