package com.crow.qqbot.componets.websocket;

import org.springframework.stereotype.Component;

import cn.hutool.core.util.URLUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.log4j.Log4j2;

/**
 * <p>
 * 
 * 解析处理URI
 * </p>
 * 
 * @author Crow
 * @version 0.0.1
 * @since 2020年11月17日 下午5:18:58
 */
@Log4j2
@Component
@Sharable
public class QQSocketUriHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	/**
	 * 经过测试，在 ws 的 uri 后面不能传递参数，不然在 netty 实现 websocket 协议握手的时候会出现断开连接的情况。 针对这种情况在
	 * websocketHandler 之前做了一层 地址过滤，然后重写 request 的 uri，并传入下一个管道中，基本上解决了这个问题。
	 */
	@Override
	public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		String uri = request.uri();
		log.info("uri:[{}]", uri);

		request.setUri(URLUtil.getPath(uri));
		ctx.fireChannelRead(request.retain());

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.channel().close();
		// 关闭连接
		log.error("关闭连接");
	}

}
