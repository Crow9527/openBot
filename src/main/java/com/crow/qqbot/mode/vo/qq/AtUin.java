package com.crow.qqbot.mode.vo.qq;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * @的用户
 *      </p>
 * 
 * @author crow
 * @since 2023年8月4日 上午10:22:15
 */
@Getter
@Setter
public class AtUin {

	/**
	 * 昵称
	 */
	@JSONField(name = "Nick")
	private String Nick;

	/**
	 * QQ
	 */
	@JSONField(name = "Uin")
	private Long Uin;

}
