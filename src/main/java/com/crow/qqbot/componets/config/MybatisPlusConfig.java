package com.crow.qqbot.componets.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * <p>
 * </p>
 * 
 * @author Crow
 * @version 0.0.1
 * @since 2019年12月10日下午2:03:01
 */

@Configuration
public class MybatisPlusConfig {
	
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		// 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setLimit(500);
		return paginationInterceptor;
	}
	
	/**
	 * 返回map自动转驼峰配置
	 * @return
	 */
	@Bean
	public ConfigurationCustomizer mybatisConfigurationCustomizer(){
	    return new ConfigurationCustomizer() {
	        @Override        
	        public void customize(org.apache.ibatis.session.Configuration configuration) {
	            configuration.setObjectWrapperFactory(new MybatisMapWrapperFactory());
	            //开启map映射没值时也返回字段
	            configuration.setCallSettersOnNulls(true);
	        }
	    };
	}

}
