package com.crow.qqbot.componets.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crow.qqbot.componets.config.CustomConfig;

/**
 * <p>
 * 处理跨域问题 (先走filter，到达servlet后才进行拦截器的处理。避免拦截器与跨域配置冲突)
 * </p>
 * 
 * @author Crow
 * @version 0.0.1
 * @since 2019年12月26日下午1:58:54
 */
@Component
public class CorsFilter implements Filter {

	@Autowired
	private CustomConfig customConfig;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		// 允许访问的客户端域名
		response.setHeader("Access-Control-Allow-Origin", customConfig.getAllowOrigin());
		// 是否允许请求带有验证信息
		response.setHeader("Access-Control-Allow-Credentials", "true");
		// 允许访问的方法名,GET POST等
		response.setHeader("Access-Control-Allow-Methods", "*");
		// 设置预检有效期，单位为秒。有效期内，不会重复发送预检请求（设置有效时间为4小时）
		response.setHeader("Access-Control-Max-Age", "14400");
		// 允许服务端访问的客户端请求头
		response.setHeader("Access-Control-Allow-Headers",
				"Accept, Accept-Encoding,Accept-Language,Connection,Content-Length,Content-Type,Host,Origin,Referer,Authorization,User-Agent");
		chain.doFilter(req, res);
	}
}
