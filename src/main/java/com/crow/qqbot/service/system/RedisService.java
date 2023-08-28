package com.crow.qqbot.service.system;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * redisService
 * </p>
 * 
 * @author Crow
 * @version 0.0.1
 */
@Component
public class RedisService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 批量删除对应的value
	 * 
	 * @param keys
	 */
	public void remove(String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * 批量删除key
	 * 
	 * @param pattern
	 */
	public void removePattern(String pattern) {
		Set<String> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}

	/**
	 * 删除对应的value
	 * 
	 * @param key
	 */
	public void remove(String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * 判断缓存中是否有对应的value
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 读取缓存
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @param expireTime 存活时间(秒)
	 * @return
	 */
	public void set(String key, Object value, long expireTime) {
		redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
	}

	/**
	 * 模糊匹配key,并返回所有匹配Key值
	 * 
	 * @param pattern
	 * @return
	 */
	public Set<String> getLikeKeys(String pattern) {
		return stringRedisTemplate.keys(pattern);
	}

	/**
	 * 递增
	 * 
	 * @param key        键
	 * @param delta      要增加几(大于0)
	 * @param expireTime 生存时间(秒)
	 * @return
	 */
	public long incr(String key, long delta, long expireTime) {
		if (delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		Long increment = redisTemplate.opsForValue().increment(key, delta);
		redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
		return increment.longValue();
	}

	/**
	 * 递增
	 * 
	 * @param key   键
	 * @param delta 要增加几(大于0)
	 * @return
	 */
	public long incr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta).longValue();
	}

	/**
	 * 获取key的生存时间
	 * 
	 * @param key 键
	 * @return
	 */
	public long getTtl(String key) {
		return redisTemplate.opsForValue().getOperations().getExpire(key);
	}
}
