package com.dongfg.project.api.config.quartz;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @author dongfg
 * @date 17-12-21
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QuartzConfig {

    @NonNull
    private DataSource dataSource;

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(AutowiringBeanQuartzJobFactory autowiringBeanQuartzJobFactory,
                                                     Properties quartzProperties) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setBeanName("QuartzScheduler");
        schedulerFactoryBean.setQuartzProperties(quartzProperties);
        schedulerFactoryBean.setDataSource(dataSource);

        schedulerFactoryBean.setJobFactory(autowiringBeanQuartzJobFactory);
        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(false);
        return schedulerFactoryBean;
    }
}