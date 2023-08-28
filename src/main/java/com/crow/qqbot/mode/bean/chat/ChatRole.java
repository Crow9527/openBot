package com.crow.qqbot.mode.bean.chat;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * chatGPT扮演角色表
 * </p>
 *
 * @author crow
 * @since 2023-04-14
 */
@Getter
@Setter
@TableName("chat_role")
public class ChatRole implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 角色名称
	 */
	private String name;

	/**
	 * 提示词
	 */
	private String prompts;

	/**
	 * 介绍
	 */
	private String introduce;

	/**
	 * 0未启用/1启用
	 */
	private Integer state;

}
