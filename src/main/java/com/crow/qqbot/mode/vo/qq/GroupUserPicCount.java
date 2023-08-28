package com.crow.qqbot.mode.vo.qq;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 群组用户涩图发送统计
 * </p>
 * 
 * @author Crow
 * @version 0.0.1
 * @since 2023年8月12日下午6:51:54
 */
@Getter
@Setter
public class GroupUserPicCount {

	/**
	 * 群组QQ号
	 */
	private Long groupUin;

	/**
	 * 用户QQ号
	 */
	private Long uin;

	/**
	 * 涩图发送数量
	 */
	private Integer count;

	/**
	 * 用户昵称
	 */
	private String nickName;

}
