package com.crow.qqbot.componets.websocket;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.log4j.Log4j2;

/**
 * <p>
 * netty服务
 * </p>
 * 
 * @author Crow
 * @version 0.0.1
 */
@Log4j2
@Component
public class NettyServer {

	private static final int PORT = 9528;

	private static final int BIZ_GROUP_SIZE = Runtime.getRuntime().availableProcessors() * 2;

	private static final int BIZ_THREAD_SIZE = 20;

	private static final EventLoopGroup BOSS_GROUP = new NioEventLoopGroup(BIZ_GROUP_SIZE,
			new DefaultThreadFactory("BossPetalGroup"));

	private static final EventLoopGroup WORKER_GROUP = new NioEventLoopGroup(BIZ_THREAD_SIZE,
			new DefaultThreadFactory("WorkerPetalGroup"));

	@Resource(name = "asyncServiceExecutor")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Resource
	private WebSocketServerInitializer serverInitializer;
	
	@PostConstruct
	public void init() {
		threadPoolTaskExecutor.execute(() -> {
			try {
				System.setProperty("org.jboss.netty.epollBugWorkaround", "true");

				ServerBootstrap serverBootstrap = new ServerBootstrap();

				serverBootstrap.group(BOSS_GROUP, WORKER_GROUP);

				serverBootstrap.channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.DEBUG));
				serverBootstrap.childHandler(serverInitializer);

				log.info("netty服务开启,端口:[{}],等待客户端连接 ......", PORT);

				ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync();

				channelFuture.channel().closeFuture().sync();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				destroy();
			}
		});
	}

	/**
	 * 释放资源
	 * 
	 * @throws InterruptedException
	 */
	public void destroy() {
		log.info("关闭Netty服务...............");
		try {
			if (BOSS_GROUP != null) {
				BOSS_GROUP.shutdownGracefully().sync();
			}
			if (WORKER_GROUP != null) {
				WORKER_GROUP.shutdownGracefully().sync();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
