package com.crow.qqbot.mode.vo.qq;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 群聊消息
 * </p>
 * 
 * @author crow
 * @since 2023年8月4日 上午9:52:22
 */
@Getter
@Setter
public class GroupInfo {

	@JSONField(name = "GroupCard")
	private String GroupCard;

	/**
	 * 群号
	 */
	@JSONField(name = "GroupCode")
	private Long GroupCode;

	@JSONField(name = "GroupInfoSeq")
	private Integer GroupInfoSeq;

	@JSONField(name = "GroupLevel")
	private Integer GroupLevel;

	@JSONField(name = "GroupRank")
	private Integer GroupRank;

	@JSONField(name = "GroupType")
	private Integer GroupType;

	/**
	 * 群名称
	 */
	@JSONField(name = "GroupName")
	private String GroupName;

}
