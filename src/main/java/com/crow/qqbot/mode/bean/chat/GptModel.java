package com.crow.qqbot.mode.bean.chat;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * GPT模型表
 * </p>
 *
 * @author crow
 * @since 2023-05-09
 */
@Getter
@Setter
@TableName("chat_gpt_model")
public class GptModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 模型名称
	 */
	private String name;

	/**
	 * 每次使用消耗积分数
	 */
	private Integer integral;

	/**
	 * 模型类型，1聊天/2文本补全
	 */
	private Integer type;

	/**
	 * 模型介绍
	 */
	private String prompt;

	/**
	 * 最大上下文token长度
	 */
	private Integer maxToken;

	/**
	 * 是否启用，0禁用/1启用
	 */
	private Integer state;

}
