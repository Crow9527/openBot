package com.crow.qqbot.componets.thread.async;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 * 获取线程池的监控信息
 * </p>
 * 
 * @author Crow
 * @version 0.0.1
 */
@Log4j2
public class VisiableThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

	private static final long serialVersionUID = 1L;

	private void showThreadPoolInfo(String prefix) {
		ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();
		if (null == threadPoolExecutor) {
			return;
		}
		log.debug("{},{},任务数   -> : [{}],已完成任务数   -> ::[{}],正在执行任务数  -> :[{}] ,  任务队列 数   -> :: [{}]",
				this.getThreadNamePrefix(), prefix, threadPoolExecutor.getTaskCount(),
				threadPoolExecutor.getCompletedTaskCount(), threadPoolExecutor.getActiveCount(),
				threadPoolExecutor.getQueue().size());
	}

	@Override
	public void execute(Runnable task) {
		showThreadPoolInfo("1. do execute");
		super.execute(task);
	}

	@Override
	public void execute(Runnable task, long startTimeout) {
		showThreadPoolInfo("2. do execute");
		super.execute(task, startTimeout);
	}

	@Override
	public Future<?> submit(Runnable task) {
		showThreadPoolInfo("1. do submit");
		return super.submit(task);
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		showThreadPoolInfo("2. do submit");
		return super.submit(task);
	}

	@Override
	public ListenableFuture<?> submitListenable(Runnable task) {
		showThreadPoolInfo("1. do submitListenable");
		return super.submitListenable(task);
	}

	@Override
	public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
		showThreadPoolInfo("2. do submitListenable");
		return super.submitListenable(task);
	}

}
