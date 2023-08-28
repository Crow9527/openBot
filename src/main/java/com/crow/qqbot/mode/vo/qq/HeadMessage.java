package com.crow.qqbot.mode.vo.qq;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * QQ消息头
 * </p>
 * 
 * @author crow
 * @since 2023年8月4日 上午9:47:16
 */
@Getter
@Setter
public class HeadMessage {

	/**
	 * 消息来源 好友/私聊/群组 Uid
	 */
	@JSONField(name = "FromUin")
	private Long FromUin;

	/**
	 * 群聊时为空/好友私聊有值
	 */
	@JSONField(name = "FromUid")
	private String FromUid;

	/**
	 * 接收消息对象 机器人
	 */
	@JSONField(name = "ToUin")
	private Long ToUin;

	/**
	 * 群聊时为空/好友私聊有值
	 */
	@JSONField(name = "ToUid")
	private String ToUid;

	/**
	 * 消息来源类型 3私聊 2群组 1好友
	 */
	@JSONField(name = "FromType")
	private Integer FromType;

	/**
	 * 发消息对象QQ Uin
	 */
	@JSONField(name = "SenderUin")
	private Long SenderUin;

	@JSONField(name = "SenderUid")
	private String SenderUid;

	/**
	 * 发消息对象 昵称 群组有值 私聊好友为空
	 */
	@JSONField(name = "SenderNick")
	private String SenderNick;

	/**
	 * 消息类型 可根据此值自由过滤 慢慢摸索 总之有用
	 */
	@JSONField(name = "MsgType")
	private Integer MsgType;

	/**
	 * C2c消息类型 17撤回消息
	 */
	@JSONField(name = "C2cCmd")
	private Integer C2cCmd;

	@JSONField(name = "MsgSeq")
	private Long MsgSeq;

	/**
	 * 发送消息时间
	 */
	@JSONField(name = "MsgTime")
	private Long MsgTime;

	@JSONField(name = "MsgRandom")
	private Long MsgRandom;

	@JSONField(name = "MsgUid")
	private Long MsgUid;

	/**
	 * 群聊消息
	 */
	@JSONField(name = "GroupInfo")
	private GroupInfo GroupInfo;

}
