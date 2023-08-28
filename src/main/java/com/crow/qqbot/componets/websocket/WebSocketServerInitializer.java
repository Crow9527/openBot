package com.crow.qqbot.componets.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 
 * @author crow
 *
 * @creation
 */
@Component
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

	@Autowired
	private QQSocketHandler qqSocketHandler;

	@Autowired
	private QQSocketUriHandler qqSocketUriHandler;

	@Autowired
//	private ServerIdleStateHandler idleStateHandler;

	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ChannelPipeline pipeline = socketChannel.pipeline();

		// 请求解码器
		pipeline.addLast(new HttpServerCodec());

		// 支持异步发送大的码流，一般用于发送文件流
		pipeline.addLast(new ChunkedWriteHandler());

		// 将多个消息转换成单一的消息对象
		pipeline.addLast(new HttpObjectAggregator(1024 * 512));

		// 身份验证
		pipeline.addLast(qqSocketUriHandler);

		// 心跳检测
		// pipeline.addLast(idleStateHandler);

		pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

		// 处理 websocket 和处理消息的发送
		pipeline.addLast(qqSocketHandler);
	}

}
