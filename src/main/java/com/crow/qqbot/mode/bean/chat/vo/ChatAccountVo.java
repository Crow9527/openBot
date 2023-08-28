package com.crow.qqbot.mode.bean.chat.vo;

import com.crow.qqbot.mode.bean.chat.ChatOfficialAccount;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * </p>
 * 
 * @author Crow
 * @version 0.0.1
 * @since 2023年5月13日下午4:28:01
 */
@Getter
@Setter
public class ChatAccountVo extends ChatOfficialAccount {

	private static final long serialVersionUID = -6453266639388764916L;

	/**
	 * 原密码
	 */
	private String oldPassword;

}
