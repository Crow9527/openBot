package com.crow.qqbot.mode.bean.qq;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * qq群组配置表
 * </p>
 *
 * @author qqbot
 * @since 2023-08-07
 */
@Getter
@Setter
@TableName("qq_group_config")
public class QQGroupConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 群组QQ号
	 */
	private Long groupUin;

	/**
	 * 0关闭机器人/1开启机器人
	 */
	private Integer status;

	/**
	 * 看图功能0关闭/1开启
	 */
	private Integer pic;

	/**
	 * 看图请求频率
	 */
	private Integer picRate;

	/**
	 * 0关闭/1开启
	 */
	private Integer r18;

	/**
	 * 0关闭/1开启
	 */
	private Integer gpt;

	/**
	 * GPT请求频率
	 */
	private Integer gptRate;

	/**
	 * 加群提醒0关闭/1打开
	 */
	private Integer groupJoin;

	/**
	 * 退群提醒0关闭/1打开
	 */
	private Integer groupExit;

	/**
	 * 涩图统计0关闭/1打开
	 */
	private Integer setuStatistics;

	/**
	 * 是否旋转图片，0不旋转/1旋转
	 */
	private Integer rotate;

	/**
	 * 随机事件0关闭/1打开
	 */
	private Integer randomEvent;

	/**
	 * 管理员QQ号，以“,”分隔
	 */
	private String admin;

	/**
	 * 群备注
	 */
	private String notes;

}
