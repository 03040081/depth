package com.zsc.modules.job.config;

import com.zsc.common.utils.PropertiesUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class ScheduleConfig {

    private final String QUARTZ_PROPERTIES = "quartz.properties";

    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();

        Properties properties = PropertiesUtils.getProperties(QUARTZ_PROPERTIES);

        factory.setQuartzProperties(properties);

        factory.setSchedulerName(properties.getProperty("org.quartz.scheduler.instanceName"));
        //延时启动
        factory.setStartupDelay(30);
        //
        factory.setApplicationContextSchedulerContextKey("applicationContextKey");
        //可选，QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        factory.setOverwriteExistingJobs(true);
        //设置自动启动，默认为true
        factory.setAutoStartup(true);

        return factory;
    }

}
