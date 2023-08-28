package com.crow.qqbot.componets.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;

import org.springframework.stereotype.Component;

import com.crow.qqbot.componets.websocket.NettyServer;

import lombok.extern.log4j.Log4j2;

/**
 * <p>
 * 关闭应用时的操作
 * </p>
 * 
 * @author Crow
 * @version 0.0.1
 * @since 2020-9-18 17:35:48
 */
@Log4j2
@Component
public class SystemShutDown implements DisposableBean, ExitCodeGenerator {

	@Autowired
	private NettyServer nettyServer;

	@Override
	public int getExitCode() {
		return 0;
	}

	@Override
	public void destroy() throws Exception {
		log.info("-------------------关闭系统服务-------------------");
		nettyServer.destroy();
	}
}
