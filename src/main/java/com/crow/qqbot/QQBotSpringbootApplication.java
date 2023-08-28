package com.crow.qqbot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>
 * </p>
 * 
 * @author crow
 * @since 2023年8月4日 上午9:34:13
 */
@EnableTransactionManagement // 开启事务
@MapperScan(basePackages = "com.crow.qqbot.mapper")
@EnableScheduling // 开启定时任务
@EnableCaching // 开启基于注解的缓存
@SpringBootApplication
public class QQBotSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(QQBotSpringbootApplication.class, args);
	}

}
