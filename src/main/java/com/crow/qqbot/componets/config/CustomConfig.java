package com.crow.qqbot.componets.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Setter;

/**
 * <p>
 * 获取自定义属性
 * </p>
 * 
 * @author Crow
 * @version 0.0.1
 * @since 2019年12月3日上午9:08:52
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "custom")
public class CustomConfig {

	/**
	 * 允许访问域名
	 */
	private String allowOrigin;

}
