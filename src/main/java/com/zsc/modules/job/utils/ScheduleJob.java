package com.zsc.modules.job.utils;

import com.zsc.common.utils.SpringContextUtils;
import com.zsc.modules.job.entity.ScheduleJobEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;

/**
 * 定时任务
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ScheduleJobEntity scheduleJob = (ScheduleJobEntity) jobExecutionContext.getMergedJobDataMap().get(ScheduleJobEntity.JOB_PARAM_KEY);

        log.info("[scheduleJob run] job{}", scheduleJob);

        //任务开始时间
        long startTime = System.currentTimeMillis();

        try {
            log.debug("[schedule Job ready].....jobId：" + scheduleJob.getJobId());
            Object target = SpringContextUtils.getBean(scheduleJob.getBeanName());
            Method method = target.getClass().getDeclaredMethod("run", String.class);
            method.invoke(target, scheduleJob.getParams());

            long times = System.currentTimeMillis() - startTime;
            log.info("[schedule Job time].....{}", times);
            log.debug("任务执行完毕，任务ID：" + scheduleJob.getJobId() + "  总共耗时：" + times + "毫秒");
        } catch (Exception e) {
            log.error("任务执行失败，任务ID：" + scheduleJob.getJobId(), e);

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;

            log.error("[schedule Job run error] {}", StringUtils.substring(e.toString(), 0, 2000));
        } finally {

        }
    }
}
