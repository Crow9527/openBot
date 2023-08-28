package com.crow.qqbot.componets.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.crow.qqbot.componets.filter.BodyStreamFilter;
import com.crow.qqbot.componets.filter.CorsFilter;


/**
 * <p>
 * </p>
 * 
 * @author Crow
 * @version 0.0.1
 * @since 2019年12月30日上午11:51:41
 */
@Configuration
public class FilterConfiguration {

	@Autowired
	public BodyStreamFilter bodyStreamFilter;

	@Autowired
	public CorsFilter corsFilter;

	@Bean
	public FilterRegistrationBean<CorsFilter> buildCorsFilter() {
		// 通过FilterRegistrationBean实例设置优先级可以生效,通过@WebFilter设置优先级无效
		FilterRegistrationBean<CorsFilter> filterRegistrationBean = new FilterRegistrationBean<CorsFilter>();
		// 注册自定义过滤器
		filterRegistrationBean.setFilter(corsFilter);
		// 优先级，越低越优先
		filterRegistrationBean.setOrder(2);
		// 过滤器名称
		filterRegistrationBean.setName("corsFilter");
		// 过滤所有路径
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean<BodyStreamFilter> buildBodyStreamFilter() {
		// 通过FilterRegistrationBean实例设置优先级可以生效,通过@WebFilter设置优先级无效
		FilterRegistrationBean<BodyStreamFilter> filterRegistrationBean = new FilterRegistrationBean<BodyStreamFilter>();
		// 注册自定义过滤器
		filterRegistrationBean.setFilter(bodyStreamFilter);
		// 优先级，越低越优先
		filterRegistrationBean.setOrder(3);
		// 过滤器名称
		filterRegistrationBean.setName("bodyStreamFilter");
		// 过滤所有路径
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}

}
