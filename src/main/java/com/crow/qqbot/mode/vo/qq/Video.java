package com.crow.qqbot.mode.vo.qq;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * QQ视频消息
 * </p>
 * 
 * @author crow
 * @since 2023年8月4日 上午10:28:38
 */
@Getter
@Setter
public class Video {

	@JSONField(name = "FileMd5")
	private String FileMd5;

	/**
	 * 文件大小
	 */
	@JSONField(name = "FileSize")
	private Long FileSize;

	/**
	 * 图片地址
	 */
	@JSONField(name = "Url")
	private String Url;
}
