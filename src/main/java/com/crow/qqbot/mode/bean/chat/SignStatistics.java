package com.crow.qqbot.mode.bean.chat;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 用户签到统计表
 * </p>
 *
 * @author crow
 * @since 2023-05-09
 */
@Getter
@Setter
@TableName("chat_sign_statistics")
public class SignStatistics implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 用户ID
	 */
	private Long accountId;

	/**
	 * 上次签到时间
	 */
	private LocalDateTime lastSignTime;

	/**
	 * 历史最长签到天数
	 */
	private Integer maxSignDay;

	/**
	 * 当前连续签到天数
	 */
	private Integer currentSignDay;

	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;

}
