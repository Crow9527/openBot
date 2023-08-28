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
 * 积分兑换表
 * </p>
 *
 * @author crow
 * @since 2023-04-13
 */
@Getter
@Setter
@TableName("chat_integral_exchange")
public class IntegralExchange implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 订单编号
	 */
	private String orderNo;

	/**
	 * 兑换用户ID
	 */
	private Long accountId;

	/**
	 * 积分数额
	 */
	private Integer integral;

	/**
	 * 1未兑换/2已兑换
	 */
	private Integer state;

	private Date createTime;

	private Date updateTime;

}
