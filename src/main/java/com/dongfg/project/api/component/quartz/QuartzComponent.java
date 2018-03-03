package com.dongfg.project.api.component.quartz;

import com.dongfg.project.api.component.quartz.builder.CronJob;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * quartz wrapper
 *
 * @author dongfg
 * @date 17-12-21
 */
public interface QuartzComponent {
    /**
     * 提交cron规则触发的任务
     *
     * @param cronJob job info
     */
    void submitJob(CronJob cronJob);

    /**
     * 查询任务列表
     *
     * @param group 组名
     * @param name  任务名(可选)
     * @return 任务列表
     */
    List<JobKey> getJobKeys(String group, String name);

    /**
     * 检查任务是否存在
     *
     * @param group 组名
     * @param name  任务名
     * @return 任务存在true
     */
    boolean checkExists(String group, String name);

    /**
     * 删除任务
     *
     * @param group 组名
     * @param name  任务名(可选)
     * @return 删除的任务
     */
    List<JobKey> removeJob(@Nullable String group, @Nullable String name);

    /**
     * 暂停任务
     *
     * @param group 组名
     * @param name  任务名(可选)
     * @return 暂停的任务
     */
    List<JobKey> pauseJob(@Nullable String group, @Nullable String name);

    /**
     * 恢复任务
     *
     * @param group 组名
     * @param name  任务名(可选)
     * @return 恢复的任务
     */
    List<JobKey> resumeJob(@Nullable String group, @Nullable String name);

    /**
     * 触发任务执行
     *
     * @param group 组名
     * @param name  任务名(可选)
     * @return 触发的任务
     */
    List<JobKey> triggerJob(@Nullable String group, @Nullable String name);

    /**
     * 获取触发器
     *
     * @param group trigger 组名
     * @param name  trigger 名
     * @return 触发器信息
     * @throws SchedulerException 获取失败
     */
    Trigger getTrigger(@NotNull String group, @NotNull String name) throws SchedulerException;

    /**
     * 获取触发器状态
     *
     * @param group trigger 组名
     * @param name  trigger 名
     * @return 获取触发器状态
     * @throws SchedulerException 获取失败
     */
    Trigger.TriggerState getTriggerState(@NotNull String group, @NotNull String name) throws SchedulerException;
}
