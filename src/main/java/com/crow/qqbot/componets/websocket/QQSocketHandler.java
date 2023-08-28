package com.crow.qqbot.componets.websocket;

import javax.annotation.Resource;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.log4j.Log4j2;

/**
 * 
 * @author crow
 *
 * @creation
 */
@Log4j2
@Component
@Sharable
public class QQSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	@Resource(name = "asyncServiceExecutor")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

		if (StrUtil.isBlank(msg.text())) {
			log.info("请求数据为空！！！");
			return;
		}
		JSONObject parseObject = null;
		try {
			parseObject = JSONObject.parseObject(msg.text());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("解析错误,原文:[{}]", msg.text());
			return;
		}

		JSONObject packet = parseObject.getJSONObject("CurrentPacket");

		threadPoolTaskExecutor.execute(() -> {
			EventProcessor.execute(packet);
		});
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.info("当前通道不活动................");
		close(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		close(ctx);
	}

	/**
	 * 关闭移除
	 * 
	 * @param channelHandlerContext
	 */
	private void close(ChannelHandlerContext ctx) {

		ctx.channel().close();

		log.info("IP[{}],已断开链接..................");
	}

}
