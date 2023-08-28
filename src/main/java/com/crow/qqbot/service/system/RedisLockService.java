package com.crow.qqbot.service.system;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * <p>
 * </p>
 * 
 * @author crow
 * @since 2023年8月11日 上午9:55:06
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class RedisLockService {

	private static final long DEFAULT_EXPIRE_UNUSED = 10000L;

	private final RedisLockRegistry redisLockRegistry;

	public void lock(String lockKey) {
		Lock lock = obtainLock(lockKey);
		lock.lock();
	}

	public boolean tryLock(String lockKey) {
		Lock lock = obtainLock(lockKey);
		return lock.tryLock();
	}

	public boolean tryLock(String lockKey, long seconds) {
		Lock lock = obtainLock(lockKey);
		try {
			return lock.tryLock(seconds, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			return false;
		}
	}

	public void unlock(String lockKey) {
		try {
			Lock lock = obtainLock(lockKey);
			lock.unlock();
		} catch (Exception e) {
			log.error("分布式锁 [{}] 释放异常", lockKey, e);
		} finally {
			redisLockRegistry.expireUnusedOlderThan(DEFAULT_EXPIRE_UNUSED);
		}
	}

	private Lock obtainLock(String lockKey) {
		return redisLockRegistry.obtain(lockKey);
	}

}
