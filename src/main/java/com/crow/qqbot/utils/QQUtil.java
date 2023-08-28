package com.crow.qqbot.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.crow.qqbot.mode.vo.qq.AtUin;
import com.crow.qqbot.mode.vo.qq.MessageQueue;
import com.crow.qqbot.mode.vo.qq.QQGroupInfo;
import com.crow.qqbot.mode.vo.qq.QQResponse;
import com.crow.qqbot.mode.vo.qq.ReplyTo;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;

/**
 * <p>
 * QQ消息工具类
 * </p>
 * 
 * @author crow
 * @since 2023年8月4日 上午11:21:40
 */
public class QQUtil {

	private static final String CMD_URL = "http://192.168.80.129:8086/v1/LuaApiCaller?funcname=MagicCgiCmd&timeout=15&qq=2107136060";

	private static final String UPLOAD_URL = "http://192.168.80.129:8086/v1/upload?qq=2107136060";

	/**
	 * 发送消息
	 * 
	 * @param MessageQueue
	 * @return
	 */
	public static QQResponse sendMessage(MessageQueue messageQueue) {
		QQResponse qqResponse = null;

		try {
			JSONObject data = new JSONObject();

			data.put("CgiCmd", "MessageSvc.PbSendMsg");

			JSONObject request = new JSONObject();
			request.put("ToUin", messageQueue.getToUin());
			request.put("ToType", messageQueue.getToType());

			// 有文本才添加
			if (StrUtil.isNotBlank(messageQueue.getMessage())) {
				request.put("Content", messageQueue.getMessage());
			}

			// 只有是群组时才能@
			if (messageQueue.getToType().equals(2) && null != messageQueue.getAtUin()) {
				JSONArray atUinArray = new JSONArray();
				atUinArray.add(messageQueue.getAtUin());
				request.put("AtUinLists", atUinArray);
			}

			// 有图片才添加
			if (CollectionUtil.isNotEmpty(messageQueue.getImageList())) {
				request.put("Images", messageQueue.getImageList());
			}

			// 语音才添加
			if (null != messageQueue.getVoice()) {
				request.put("Voice", messageQueue.getVoice());
			}

			data.put("CgiRequest", request);

			String body = sendRequest(data.toJSONString(), CMD_URL);

			qqResponse = JSONObject.parseObject(body, QQResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qqResponse;
	}

	/**
	 * 发送群组回复消息
	 * 
	 * @param toUin   发送的群QQ
	 * @param atUin   回复的人
	 * @param replyTo 回复的消息
	 * @param content 回复内容
	 * @return
	 */
	public static QQResponse sendGroupReplyMessage(Long toUin, AtUin atUin, ReplyTo replyTo, String content) {
		QQResponse qqResponse = null;

		try {
			JSONObject data = new JSONObject();

			data.put("CgiCmd", "MessageSvc.PbSendMsg");

			JSONObject request = new JSONObject();
			request.put("ToUin", toUin);
			request.put("ToType", 2);
			request.put("Content", content);
			request.put("ReplyTo", replyTo);

			JSONArray atUinArray = new JSONArray();
			atUinArray.add(atUin);
			atUinArray.add(atUin);
			request.put("AtUinLists", atUinArray);

			data.put("CgiRequest", request);

			String body = sendRequest(data.toJSONString(), CMD_URL);

			qqResponse = JSONObject.parseObject(body, QQResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qqResponse;
	}

	/**
	 * 获取群组成员信息
	 * 
	 * @param groupUin 群QQ
	 * @return
	 */
	public static QQResponse getGroupUserList(Long groupUin) {
		QQResponse qqResponse = null;

		try {
			JSONObject data = new JSONObject();

			data.put("CgiCmd", "GetGroupMemberLists");

			JSONObject request = new JSONObject();
			request.put("Uin", groupUin);
			request.put("LastBuffer", "");

			data.put("CgiRequest", request);

			String body = sendRequest(data.toJSONString(), CMD_URL);

			qqResponse = JSONObject.parseObject(body, QQResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qqResponse;
	}

	/**
	 * 撤回群组消息
	 * 
	 * @param uin       群QQ
	 * @param msgSeq    来自websocket推送
	 * @param msgRandom 来自websocket推送
	 * @return
	 */
	public static QQResponse revokeGroupMessage(Long uin, Long msgSeq, Long msgRandom) {
		QQResponse qqResponse = null;

		try {
			JSONObject data = new JSONObject();

			data.put("CgiCmd", "GroupRevokeMsg");

			JSONObject request = new JSONObject();
			request.put("Uin", uin);
			request.put("MsgSeq", msgSeq);
			request.put("MsgRandom", msgRandom);

			data.put("CgiRequest", request);

			String body = sendRequest(data.toJSONString(), CMD_URL);

			qqResponse = JSONObject.parseObject(body, QQResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qqResponse;
	}

	/**
	 * 上传资源文件
	 * 
	 * @param commandId 上传文件类型 1好友图片 2群组图片 26好友语音 29群组语音
	 * @param base64Str
	 * @return
	 */
	public static QQResponse upload(Integer commandId, String base64Str) {

		QQResponse qqResponse = null;

		try {
			JSONObject data = new JSONObject();

			data.put("CgiCmd", "PicUp.DataUp");

			JSONObject request = new JSONObject();
			request.put("CommandId", commandId);

			// 三者任选其一，不能同时存在
			request.put("Base64Buf", base64Str);
			// request.put("FileUrl", base64Str);
			// request.put("FilePath", base64Str);

			data.put("CgiRequest", request);

			String body = sendRequest(data.toJSONString(), UPLOAD_URL);

			qqResponse = JSONObject.parseObject(body, QQResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return qqResponse;
	}

	/**
	 * 根据uid获取群员信息
	 * 
	 * @param uid
	 * @return
	 */
	public static QQGroupInfo getUinByUid(String uid) {
		QQGroupInfo qqGroupInfo = null;

		try {
			JSONObject data = new JSONObject();

			data.put("CgiCmd", "QueryUinByUid");

			JSONObject request = new JSONObject();
			request.put("Uid", uid);

			data.put("CgiRequest", request);

			String body = sendRequest(data.toJSONString(), CMD_URL);

			qqGroupInfo = JSONObject.parseObject(body).getJSONArray("ResponseData").getObject(0, QQGroupInfo.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return qqGroupInfo;

	}

	/**
	 * 发送请求
	 * 
	 * @param data
	 * @param url
	 * @return
	 */
	private static String sendRequest(String data, String url) {
		return HttpUtil.createPost(url).body(data).execute().body();
	}

	public static void main(String[] args) {
		MessageQueue messageQueue = new MessageQueue();
		messageQueue.setToUin(615256275L);
		messageQueue.setToType(2);
		messageQueue.setMessage("");
		QQResponse sendTextMessage = sendMessage(messageQueue);
		System.err.println(JSONObject.toJSONString(sendTextMessage));
	}

}
