package raf.rs.WebProject2025.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20); // Početni broj niti
        executor.setMaxPoolSize(50);  // Maksimalni broj niti
        executor.setQueueCapacity(500); // Kapacitet reda čekanja
        executor.setThreadNamePrefix("async-task-");
        executor.initialize();
        return executor;
    }
}
