package com.crow.qqbot.componets.websocket;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.log4j.Log4j2;

/**
 * 
 * <p>
 * 空闲检测
 * </p>
 * 
 * @author crow
 * @since 2022年8月2日 下午3:00:27
 */
@Log4j2
@Component
@Sharable
public class ServerIdleStateHandler extends IdleStateHandler {

	/**
	 * 设置读取超时时间为 300秒
	 */
	private static final int READER_IDLE_TIME = 300;

	public ServerIdleStateHandler() {
		super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
	}

	@Override
	protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
		log.warn("[{}]秒内没有读取到来自IP:[{}]的数据,关闭连接");
		ctx.channel().close();
	}

}
