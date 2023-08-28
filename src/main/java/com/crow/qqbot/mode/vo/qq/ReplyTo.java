package com.crow.qqbot.mode.vo.qq;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * </p>
 * 
 * @author crow
 * @since 2023年8月7日 下午5:18:53
 */
@Getter
@Setter
public class ReplyTo {

	@JSONField(name = "MsgSeq")
	private Long MsgSeq;

	@JSONField(name = "MsgTime")
	private Long MsgTime;

	@JSONField(name = "MsgUid")
	private Long MsgUid;

}
