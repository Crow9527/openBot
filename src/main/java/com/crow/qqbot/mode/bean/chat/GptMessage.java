package com.crow.qqbot.mode.bean.chat;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 网页GPT发送消息表
 * </p>
 *
 * @author crow
 * @since 2023-04-02
 */
@Getter
@Setter
@TableName("chat_gpt_message")
public class GptMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "message_id", type = IdType.AUTO)
	private Integer messageId;

	/**
	 * 用户id
	 */
	private Long accountId;

	/**
	 * 当前会话
	 */
	private String sessions;

	/**
	 * ip地址
	 */
	private String ip;

	/**
	 * 用户请求问题
	 */
	private String question;

	/**
	 * 消息类型 1文本消息/2图片消息/3设置角色
	 */
	private Integer messageType;

	/**
	 * 使用的GPT模型
	 */
	private String gptModel;

	/**
	 * 角色ID
	 */
	private Integer roleId;

	/**
	 * 响应文本消息
	 */
	private String response;

	/**
	 * 状态 0删除/1正常
	 */
	private Integer state;

	/**
	 * 提问消耗的tokens数量
	 */
	private Integer questionTokens;

	/**
	 * 响应消耗的tokens数量
	 */
	private Integer responseTokens;

	/**
	 * 包含上下文总消耗的tokens数量
	 */
	private Integer tokens;

	/**
	 * 发送时间
	 */
	private LocalDateTime sendTime;

	/**
	 * 响应时间
	 */
	private LocalDateTime responseTime;

}
