package com.crow.qqbot.componets.thread.async;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 * </p>
 * 
 * @author Crow
 * @version 0.0.1
 */
@Configuration // 表示这个类是配置类
@EnableAsync  // 启用异步任务
public class AsyncExecutorConfig {
	private static final Logger logger = LogManager.getLogger(AsyncExecutorConfig.class);

	@Bean
	public ThreadPoolTaskExecutor asyncServiceExecutor() {
		logger.info("start asyncServiceExecutor");
		ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
		int corePoolSize = Runtime.getRuntime().availableProcessors();
		// 配置核心线程数
		executor.setCorePoolSize(corePoolSize * 2);
		// 配置最大线程数
		executor.setMaxPoolSize(corePoolSize * 2);
		// 配置队列大小
		executor.setQueueCapacity(99999);
		// 配置线程池中的线程的名称前缀
		executor.setThreadNamePrefix("async-service-");
		// rejection-policy：当pool已经达到max size的时候，并且队列已经满了，如何处理新任务
		// CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
		// DiscardPolicy: 直接丢弃
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
		// 执行初始化
		executor.initialize();
		// 等待所有任务结束后再关闭线程池
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}

}
