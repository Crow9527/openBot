package com.crow.qqbot.mode.vo.qq;

import java.util.ArrayList;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * </p>
 * 
 * @author crow
 * @since 2023年8月4日 上午9:47:41
 */
@Getter
@Setter
public class BodyMessage {

	/**
	 * 0为单一或复合类型消息(文字 At 图片 语音自由组合) /12 Xml消息 /19 Video消息 /51 JSON卡片消息
	 */
	@JSONField(name="SubMsgType")
	private Integer SubMsgType;

	/**
	 * 接受的内容 文字/XML/JSON
	 */
	@JSONField(name="Content")
	private String Content;

	/**
	 * @列表
	 */
	@JSONField(name="AtUinLists")
	private ArrayList<AtUin> AtUinLists;

	/**
	 * 图片数组
	 */
	@JSONField(name="Images")
	private ArrayList<Image> Images;

	/**
	 * 视频
	 */
	@JSONField(name="Video")
	private Video Video;

	/**
	 * 语音
	 */
	@JSONField(name="Voice")
	private Voice Voice;
}
