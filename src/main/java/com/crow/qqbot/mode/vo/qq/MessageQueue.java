package com.crow.qqbot.mode.vo.qq;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 群组消息队列
 * </p>
 * 
 * @author crow
 * @since 2023年8月11日 上午10:44:56
 */
@Getter
@Setter
public class MessageQueue {

	/**
	 * 发送的群QQ/好友的QQ
	 */
	private Long toUin;

	/**
	 * 发送类型 1好友/2群组
	 */
	private Integer toType;

	/**
	 * @的人
	 */
	private AtUin atUin;

	/**
	 * 发送的消息
	 */
	private String message;

	/**
	 * 上传文件类型 1好友图片 2群组图片 26好友语音 29群组语音
	 */
	private Integer commandId;

	/**
	 * 是否是撤回消息 0不撤回/1撤回
	 */
	private Integer revoke = 0;

	/**
	 * 资源文件MD5列表
	 */
	private ArrayList<String> imageMd5List;

	/**
	 * 图片列表
	 */
	private ArrayList<Image> imageList;

	/**
	 * 语音
	 */
	private Voice voice;

	/**
	 * 语音文件MD5
	 */
	private String voiceMd5;

}
