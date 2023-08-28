package com.crow.qqbot.mode.bean.chat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 积分日志
 * </p>
 *
 * @author crow
 * @since 2023-04-13
 */
@Setter
@Getter
@TableName("chat_integral_log")
public class IntegralLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 用户ID
	 */
	private Long accountId;

	/**
	 * 消耗、获得积分数量
	 */
	private Integer integral;

	/**
	 * 0用户注册/1消耗积分/2签到获得积分/3兑换获得积分/4邀请人员获得积分/5系统补偿
	 */
	private Integer type;

	/**
	 * 发送消息的ID
	 */
	private Integer messageId;

	/**
	 * 积分变化说明
	 */
	private String explains;

	/**
	 * 订单编号
	 */
	private String orderNo;

	/**
	 * 剩余积分
	 */
	private Integer currentIntegral;

	private Date createTime;

}
