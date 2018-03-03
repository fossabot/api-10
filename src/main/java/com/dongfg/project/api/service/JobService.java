package com.dongfg.project.api.service;

import com.dongfg.project.api.component.quartz.QuartzComponent;
import com.dongfg.project.api.dto.CommonResponse;
import com.dongfg.project.api.dto.JobInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dongfg
 * @date 18-1-29
 */
@Service
@Slf4j
public class JobService {

    @Autowired
    private QuartzComponent quartzComponent;

    public List<JobInfo> jobInfoList() {
        // get all
        List<JobKey> jobKeys = quartzComponent.getJobKeys(null, null);
        List<JobInfo> jobInfoList = new ArrayList<>();
        jobKeys.forEach(jobKey -> {
            JobInfo jobInfo = JobInfo.builder()
                    .group(jobKey.getGroup())
                    .name(jobKey.getName())
                    .build();
            try {
                Trigger.TriggerState triggerState = quartzComponent.getTriggerState(jobKey.getGroup(), jobKey.getName());
                jobInfo.setState(triggerState.name());
            } catch (SchedulerException ignore) {
                jobInfo.setState("UNKNOWN");
            }
            try {
                Trigger trigger = quartzComponent.getTrigger(jobKey.getGroup(), jobKey.getName());
                jobInfo.setPrevTime(trigger.getPreviousFireTime());
                jobInfo.setNextTime(trigger.getNextFireTime());
            } catch (SchedulerException ignore) {
            }
            jobInfoList.add(jobInfo);
        });

        return jobInfoList;
    }

    /**
     * 切换任务状态
     */
    public CommonResponse<Void> toggleJob(String group, String name) {
        CommonResponse<Void> response = CommonResponse.<Void>builder().success(false).build();
        try {
            Trigger.TriggerState triggerState = quartzComponent.getTriggerState(group, name);
            if (triggerState.equals(Trigger.TriggerState.PAUSED)) {
                quartzComponent.resumeJob(group, name);
                response.setSuccess(true);
            }

            if (triggerState.equals(Trigger.TriggerState.NORMAL)) {
                quartzComponent.pauseJob(group, name);
                response.setSuccess(true);
            }

        } catch (SchedulerException ex) {
            log.error("job toggle exception", ex);
            response.setMsg(ex.getMessage());
        }

        return response;
    }
}
