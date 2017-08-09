package com.zhy.server.server.ui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Created by Viki on 2017/6/7.
 * Function: 定时任务所属线程池配置
 */
@Configuration
@EnableAsync
public class SchedulingConfigurerConfiguration{

    @Bean
    public Executor simpleAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setThreadNamePrefix("simpleAsync-");
        executor.initialize();
        return executor;
    }
}