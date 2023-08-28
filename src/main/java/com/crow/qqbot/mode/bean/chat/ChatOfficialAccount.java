package com.crow.qqbot.mode.bean.chat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 微信公众号用户
 * </p>
 *
 * @author crow
 * @since 2023-03-21
 */
@Getter
@Setter
public class ChatOfficialAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	private String openId;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 性别 0未知/1男/2女
	 */
	private Integer sex;

	/**
	 * 用户头像
	 */
	private String img;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 积分
	 */
	private Integer integral;

	/**
	 * 用户状态 0正常/1禁止使用GPT
	 */
	private Integer state;

	/**
	 * 0取消关注/1关注
	 */
	private Integer follow;

	/**
	 * 0未激活看图模式/1已激活
	 */
	private Integer active;

	/**
	 * APIkey
	 */
	private String apiKey;

	/**
	 * 加密盐
	 */
	private String salt;

	/**
	 * 邀请码
	 */
	private String invitationCode;

	/**
	 * 注册时间
	 */
	private Date createTime;

	private Date updateTime;

	/**
	 * 最后一次登录时间
	 */
	private Date lastLoginTime;

}
