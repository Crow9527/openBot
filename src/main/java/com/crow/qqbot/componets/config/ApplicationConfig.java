package com.crow.qqbot.componets.config;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * 
 * </p>
 * 
 * @author Crow
 * @version 0.0.1
 */
@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

	/**
	 * 时间转换器
	 */
	@Resource(name = "hoseJackson2HttpMessageConverter")
	private MappingJackson2HttpMessageConverter hoseJackson2HttpMessageConverter;

	// 添加转换器
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// 将我们定义的时间格式转换器添加到转换器列表中,
		// 这样jackson格式化时候但凡遇到Date类型就会转换成我们定义的格式
		converters.add(hoseJackson2HttpMessageConverter);
	}

	/**
	 * 添加拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 拦截器的顺序 就是这个配置的顺序
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").// url访问的请求路径
				addResourceLocations("classpath:/static/");// 图片存放的真实路径

	}

}
