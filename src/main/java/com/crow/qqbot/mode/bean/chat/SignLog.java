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
 * 签到历史记录表
 * </p>
 *
 * @author crow
 * @since 2023-05-09
 */
@Getter
@Setter
@TableName("chat_sign_log")
public class SignLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 用户ID
	 */
	private Long accountId;

	/**
	 * 签到时间
	 */
	private LocalDateTime signTime;

}
