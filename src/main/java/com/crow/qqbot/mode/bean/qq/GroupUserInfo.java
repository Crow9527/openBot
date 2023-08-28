package com.crow.qqbot.mode.bean.qq;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * qq群用户信息表
 * </p>
 *
 * @author crow
 * @since 2023-08-12
 */
@Getter
@Setter
@TableName("qq_group_user_info")
public class GroupUserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	private Integer id;

	private String uid;

	/**
	 * 用户qq号
	 */
	private Long uin;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 群号
	 */
	private Long groupUin;

	/**
	 * 性别0未知/1男/2女
	 */
	private Integer sex;

	/**
	 * 入群时间
	 */
	private Date joinTime;

	/**
	 * 最后发言时间
	 */
	private Date lastSpeakTime;

	/**
	 * 群内等级
	 */
	private Integer level;

}
