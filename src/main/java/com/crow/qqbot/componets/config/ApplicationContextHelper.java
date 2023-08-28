package com.crow.qqbot.componets.config;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <p>
 * </p>
 * 
 * @author Crow
 * @version 0.0.1
 * @since 2019年12月27日下午2:14:54
 */

@Component
public class ApplicationContextHelper implements ApplicationContextAware {
	private static final Logger logger = LogManager.getLogger(ApplicationContextHelper.class);
	
	private static ApplicationContext application;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		application = applicationContext;
		logger.info("-------------------ApplicationContextHelper加载完毕---------------------------------------");
	}

	/**
	 * 获取ApplicationContext
	 * 
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return application;
	}

	/**
	 * 根据BeanName获取Bean
	 * 
	 * @param <T>
	 * @param beanName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		return (T) application.getBean(beanName);
	}

	/**
	 * 获取Bean
	 * 
	 * @param <T>
	 * @param beanName
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz) {
		return (T) application.getBean(clazz);
	}

	/**
	 * 获取相同了类型的bean
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
		return (Map<String, T>) application.getBeansOfType(clazz);
	}

	/**
	 * 创建bean
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> T createBean(Class<T> clazz) {
		return getApplicationContext().getAutowireCapableBeanFactory().createBean(clazz);
	}

	/**
	 * 清除ApplicationContext
	 */
	public static void clearApplicationContext() {
		application = null;
	}

}
