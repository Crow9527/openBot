package com.crow.qqbot.mode.vo.qq;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 群聊事件
 * </p>
 * 
 * @author crow
 * @since 2023年8月4日 下午8:29:14
 */
@Getter
@Setter
public class HeadEvent {

	/**
	 * 处理人Uid
	 */
	@JSONField(name = "AdminUid")
	private String AdminUid;

	/**
	 * 进群者Uid
	 */
	@JSONField(name = "Uid")
	private String Uid;

}
