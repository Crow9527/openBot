package com.crow.qqbot.componets.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.crow.qqbot.componets.wrapper.BodyReaderHttpServletRequestWrapper;


/**
 * <p>
 * </p>
 * 
 * @author Crow
 * @version 0.0.1
 * @since 2020年5月9日上午9:22:17
 */
@Component
public class BodyStreamFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// 防止流读取一次后就没有了, 所以需要将流继续写出去
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);

		chain.doFilter(requestWrapper, response);

	}

	@Override
	public void destroy() {

	}

}
