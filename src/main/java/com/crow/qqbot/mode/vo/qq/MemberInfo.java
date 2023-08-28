package com.crow.qqbot.mode.vo.qq;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * </p>
 * 
 * @author crow
 * @since 2023年8月12日 下午10:07:28
 */

@Getter
@Setter
public class MemberInfo {

	@JSONField(name = "CreditLevel")
	private Long CreditLevel;

	/**
	 * 入群时间
	 */
	@JSONField(name = "JoinTime")
	private Long JoinTime;

	/**
	 * 最后发言时间
	 */
	@JSONField(name = "LastSpeakTime")
	private Long LastSpeakTime;

	/**
	 * 群内等级
	 */
	@JSONField(name = "Level")
	private Integer Level;

	@JSONField(name = "MemberFlag")
	private Long MemberFlag;

	/**
	 * 用户昵称
	 */
	@JSONField(name = "Nick")
	private String Nick;

	@JSONField(name = "Uid")
	private String Uid;

	/**
	 * 用户QQ号
	 */
	@JSONField(name = "Uin")
	private Long Uin;

}
