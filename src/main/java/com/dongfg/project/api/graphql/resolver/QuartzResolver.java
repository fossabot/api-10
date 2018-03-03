package com.dongfg.project.api.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dongfg.project.api.component.quartz.QuartzComponent;
import com.dongfg.project.api.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dongfg
 * @date 17-12-21
 */
@Component
@Slf4j
public class QuartzResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private CommonService commonService;

    @Autowired
    private QuartzComponent quartzComponent;

    /**
     * 查询任务列表
     *
     * @param group 组名
     * @param name  任务名
     * @return affect jobKeys
     */
    public List<JobKey> jobKeys(String group, String name) {
        return quartzComponent.getJobKeys(group, name);
    }

    /**
     * 删除任务
     *
     * @param totpCode 两步验证令牌
     * @param group    组名
     * @param name     任务名
     * @return affect jobKeys
     */
    public List<JobKey> removeJob(int totpCode, String group, String name) {
        if (commonService.invalidOtpCode(totpCode)) {
            throw new RuntimeException("invalid otp code");
        }

        return quartzComponent.removeJob(group, name);
    }

    /**
     * 暂停任务
     *
     * @param totpCode 两步验证令牌
     * @param group    组名
     * @param name     任务名
     * @return affect jobKeys
     */
    public List<JobKey> pauseJob(int totpCode, String group, String name) {
        if (commonService.invalidOtpCode(totpCode)) {
            throw new RuntimeException("invalid otp code");
        }

        return quartzComponent.pauseJob(group, name);
    }

    /**
     * 恢复任务
     *
     * @param totpCode 两步验证令牌
     * @param group    组名
     * @param name     任务名
     * @return affect jobKeys
     */
    public List<JobKey> resumeJob(int totpCode, String group, String name) {
        if (commonService.invalidOtpCode(totpCode)) {
            throw new RuntimeException("invalid otp code");
        }

        return quartzComponent.resumeJob(group, name);
    }

    /**
     * 触发任务
     *
     * @param totpCode 两步验证令牌
     * @param group    组名
     * @param name     任务名
     * @return affect jobKeys
     */
    public List<JobKey> triggerJob(int totpCode, String group, String name) {
        if (commonService.invalidOtpCode(totpCode)) {
            throw new RuntimeException("invalid otp code");
        }

        return quartzComponent.triggerJob(group, name);
    }
}
