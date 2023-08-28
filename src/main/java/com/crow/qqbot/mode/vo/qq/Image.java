package com.crow.qqbot.mode.vo.qq;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * </p>
 * 
 * @author crow
 * @since 2023年8月4日 上午10:17:48
 */
@Getter
@Setter
public class Image {

	/**
	 * 文件ID
	 */
	@JSONField(name = "FileId")
	private Long FileId;

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
