package com.crow.qqbot.mode.bean.chat;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 邀请记录表
 * </p>
 *
 * @author crow
 * @since 2023-04-11
 */
@Getter
@Setter
@TableName("chat_invitation_log")
public class InvitationLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 邀请人ID
	 */
	private Long inviterId;

	/**
	 * 被邀请人ID
	 */
	private Long inviteeId;

	/**
	 * 奖励积分
	 */
	private Integer integral;

	private Date createTime;

}
