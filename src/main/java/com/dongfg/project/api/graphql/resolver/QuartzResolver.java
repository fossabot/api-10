package com.dongfg.project.api.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dongfg.project.api.component.quartz.QuartzComponent;
import com.dongfg.project.api.service.CommonService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dongfg
 * @date 17-12-21
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QuartzResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @NonNull
    private CommonService commonService;

    @NonNull
    private QuartzComponent quartzComponent;

    /**
     * 查询任务列表
     *
     * @param group 组名
     * @param name  任务名
     * @return affect jobKeys
     */
    public List<JobKey> getJobKeys(String group, String name) {
        return quartzComponent.getJobKeys(group, name);
    }

    /**
     * 删除任务
     *
     * @param otpCode 两步验证令牌
     * @param group   组名
     * @param name    任务名
     * @return affect jobKeys
     */
    public List<JobKey> removeJob(int otpCode, String group, String name) {
        if (!commonService.validateOtpCode(otpCode)) {
            throw new RuntimeException("invalid otp code");
        }

        return quartzComponent.removeJob(group, name);
    }

    /**
     * 暂停任务
     *
     * @param otpCode 两步验证令牌
     * @param group   组名
     * @param name    任务名
     * @return affect jobKeys
     */
    public List<JobKey> pauseJob(int otpCode, String group, String name) {
        if (!commonService.validateOtpCode(otpCode)) {
            throw new RuntimeException("invalid otp code");
        }

        return quartzComponent.pauseJob(group, name);
    }

    /**
     * 恢复任务
     *
     * @param otpCode 两步验证令牌
     * @param group   组名
     * @param name    任务名
     * @return affect jobKeys
     */
    public List<JobKey> resumeJob(int otpCode, String group, String name) {
        if (!commonService.validateOtpCode(otpCode)) {
            throw new RuntimeException("invalid otp code");
        }

        return quartzComponent.resumeJob(group, name);
    }
}
