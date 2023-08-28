package com.crow.qqbot.mode.vo.qq;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * </p>
 * 
 * @author crow
 * @since 2023年8月4日 下午4:44:25
 */
@Getter
@Setter
public class QQResponse {

	@JSONField(name = "ResponseData")
	private ResponseData responseData;

	@JSONField(name = "CgiBaseResponse")
	private CgiBaseResponse cgiBaseResponse;

	@Getter
	@Setter
	public class ResponseData {

		@JSONField(name = "MsgSeq")
		private Long MsgSeq;

		@JSONField(name = "MsgTime")
		private Long MsgTime;

		@JSONField(name = "FileMd5")
		private String FileMd5;

		@JSONField(name = "FileSize")
		private Long FileSize;

		@JSONField(name = "FileId")
		private Long FileId;

		@JSONField(name = "FileToken")
		private String FileToken;

		@JSONField(name = "Status")
		private String Status;

		@JSONField(name = "Uin")
		private Long Uin;

		@JSONField(name = "LastBuffer")
		private String LastBuffer;

		@JSONField(name = "MemberLists")
		private List<MemberInfo> MemberLists;
	}

	@Getter
	@Setter
	public class CgiBaseResponse {
		/**
		 * 状态码 0正常/34未知错误，跟消息长度似乎无关，可以尝试分段重新发送/ 110发送失败，你已被移出该群，请重新加群/ 120机器人被禁言/
		 * 241消息发送频率过高，对同一个群或好友，建议发消息的最小间隔控制在1100ms/ 299超过群发言频率限制
		 */
		@JSONField(name = "Ret")
		private Integer Ret;

		@JSONField(name = "ErrMsg")
		private String ErrMsg;

	}

}
