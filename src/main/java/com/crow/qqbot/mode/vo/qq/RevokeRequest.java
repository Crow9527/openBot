package com.crow.qqbot.mode.vo.qq;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 撤回消息请求体
 * </p>
 * 
 * @author Crow
 * @version 0.0.1
 * @since 2023年8月5日上午11:55:53
 */
@Getter
@Setter
@ToString
public class RevokeRequest {
	@JSONField(name = "Uin")
	private Long Uin;

	@JSONField(name = "MsgSeq")
	private Long MsgSeq;

	@JSONField(name = "MsgRandom")
	private Long MsgRandom;

	private transient Date sendDate;

	private transient Date revokeDate;
}
