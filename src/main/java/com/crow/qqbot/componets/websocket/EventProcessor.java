package com.crow.qqbot.componets.websocket;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.crow.qqbot.componets.config.ApplicationContextHelper;
import com.crow.qqbot.componets.config.SystemConfig;
import com.crow.qqbot.componets.constant.BotMessage;
import com.crow.qqbot.componets.constant.DigitalConversionDict;
import com.crow.qqbot.mode.bean.qq.GroupUserInfo;
import com.crow.qqbot.mode.bean.qq.QQGroupConfig;
import com.crow.qqbot.mode.vo.qq.AtUin;
import com.crow.qqbot.mode.vo.qq.BodyMessage;
import com.crow.qqbot.mode.vo.qq.HeadEvent;
import com.crow.qqbot.mode.vo.qq.HeadMessage;
import com.crow.qqbot.mode.vo.qq.MessageQueue;
import com.crow.qqbot.mode.vo.qq.QQGroupInfo;
import com.crow.qqbot.mode.vo.qq.RevokeRequest;
import com.crow.qqbot.service.qq.GroupUserInfoService;
import com.crow.qqbot.service.qq.QQGroupConfigService;
import com.crow.qqbot.service.system.RedisService;
import com.crow.qqbot.utils.QQUtil;
import com.crow.qqbot.utils.RandomPicUtil;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 事件处理器
 * </p>
 * 
 * @author crow
 * @since 2023年8月4日 下午5:03:05
 */
public class EventProcessor {

	/**
	 * 机器人QQ号
	 */
	private static final Long BOT = new Long(2107136060L);

	public static void execute(JSONObject packet) {
		String event = packet.getString("EventName");

		JSONObject eventData = packet.getJSONObject("EventData");

//		System.err.println(eventData.toJSONString());

		HeadMessage headMessage = eventData.getObject("MsgHead", HeadMessage.class);
		BodyMessage bodyMessage = eventData.getObject("MsgBody", BodyMessage.class);
		HeadEvent headEvent = eventData.getObject("Event", HeadEvent.class);

		switch (event) {
		case "ON_EVENT_FRIEND_NEW_MSG":
			// 收到好友/私聊消息事件
			friendNewMsg(headMessage, bodyMessage);
			break;
		case "ON_EVENT_GROUP_NEW_MSG":
			// 收到群组消息事件
			groupNewMsg(headMessage, bodyMessage);
			break;
		case "ON_EVENT_GROUP_SYSTEM_MSG_NOTIFY":
			// 群组系统消息事件
			break;
		case "ON_EVENT_GROUP_INVITE":
			// 群组邀请事件
			break;
		case "ON_EVENT_GROUP_EXIT":
			// 退群事件
			groupExit(headMessage, headEvent);
			break;
		case "ON_EVENT_GROUP_JOIN":
			// 进群事件
			groupJoin(headMessage, headEvent);
			break;
		default:
			break;
		}
	}

	/**
	 * 收到好友/私聊消息
	 * 
	 * @param headMessage
	 * @param bodyMessage
	 */
	public static void friendNewMsg(HeadMessage headMessage, BodyMessage bodyMessage) {

		if (null == bodyMessage || StrUtil.isBlank(bodyMessage.getContent())) {
			return;
		}

		String content = bodyMessage.getContent();

		String[] split = content.split(" ");

		// 获取发消息的对象
		Long sendUin = headMessage.getSenderUin();

		Long adminUin = new Long(1047337105L);

		if ("开启".equals(split[0]) || "关闭".equals(split[0])) {
			if (!adminUin.equals(sendUin)) {
				return;
			}

			// 状态
			int state = "开启".equals(split[0]) ? 1 : 0;

			// 修改配置的群组
			Long groupUin = new Long(split[1]);

			String sendMessge = setFunction(groupUin, 2, state, split[2]);

			MessageQueue messageQueue = new MessageQueue();
			messageQueue.setToUin(sendUin);
			messageQueue.setToType(1);
			messageQueue.setMessage(sendMessge);

			QQUtil.sendMessage(messageQueue);

		} else if ("新增配置".equals(split[0])) {
			if (!adminUin.equals(sendUin)) {
				return;
			}
			QQGroupConfig updateConfig = new QQGroupConfig();
			Long groupUin = new Long(split[1]);
			updateConfig.setGroupUin(groupUin);

			QQGroupConfigService qqGroupConfigService = ApplicationContextHelper.getBean(QQGroupConfigService.class);
			qqGroupConfigService.save(updateConfig);

			String sendMessge = String.format("群组[%s]，%s", groupUin, "配置新增成功");

			MessageQueue messageQueue = new MessageQueue();
			messageQueue.setToUin(sendUin);
			messageQueue.setToType(1);
			messageQueue.setMessage(sendMessge);

			QQUtil.sendMessage(messageQueue);

			// 推送群组消息
			messageQueue.setToUin(groupUin);
			messageQueue.setToType(2);
			QQUtil.sendMessage(messageQueue);

		} else if ("移除".equals(split[0])) {
			if (!adminUin.equals(sendUin)) {
				return;
			}

			QQGroupConfigService qqGroupConfigService = ApplicationContextHelper.getBean(QQGroupConfigService.class);

			Long groupUin = new Long(split[1]);

			qqGroupConfigService
					.remove(Wrappers.lambdaUpdate(new QQGroupConfig()).eq(QQGroupConfig::getGroupUin, groupUin));

			String sendMessge = String.format("群组[%s]，%s", groupUin, "配置删除成功");

			MessageQueue messageQueue = new MessageQueue();
			messageQueue.setToUin(sendUin);
			messageQueue.setToType(1);
			messageQueue.setMessage(sendMessge);

			QQUtil.sendMessage(messageQueue);

		}

	}

	/**
	 * 收到群组消息事件
	 * 
	 * @param headMessage
	 * @param bodyMessage
	 */
	public static void groupNewMsg(HeadMessage headMessage, BodyMessage bodyMessage) {

		// 获取消息来源
		Long fromUin = headMessage.getFromUin();

		QQGroupConfig qqGroupConfig = SystemConfig.GROUP_LIST.stream()
				.filter(config -> config.getGroupUin().equals(fromUin))
				.findFirst()
				.orElse(null);

		if (null == qqGroupConfig) {// 没有该群的配置
			return;
		}

		// 获取发消息的对象
		Long sendUin = headMessage.getSenderUin();

		// 获取发消息对象的昵称
		String sendUserName = headMessage.getSenderNick();

		if (BOT.equals(sendUin)) {// 机器人的消息

			StringBuilder stringBuilder = new StringBuilder("机器人消息:");
			stringBuilder.append("Uin:")
					.append(fromUin)
					.append(",MsgSeq:")
					.append(headMessage.getMsgSeq())
					.append(",MsgRandom:")
					.append(headMessage.getMsgRandom());

			if (null != bodyMessage) {
				stringBuilder.append(JSONObject.toJSONString(bodyMessage));
			}
			System.err.println(stringBuilder.toString());

			if (null != bodyMessage && StrUtil.isNotBlank(bodyMessage.getContent())) {
				if (bodyMessage.getContent().indexOf(SystemConfig.REVOKE_MARK) > -1) {
					RevokeRequest revokeRequest = new RevokeRequest();
					Date date = new Date();
					revokeRequest.setUin(fromUin);
					revokeRequest.setMsgSeq(headMessage.getMsgSeq());
					revokeRequest.setMsgRandom(headMessage.getMsgRandom());
					revokeRequest.setSendDate(date);
					revokeRequest.setRevokeDate(DateUtil.offset(date, DateField.SECOND, 10));
					SystemConfig.REVOKE_MESSAGE_MAP.put(System.currentTimeMillis(), revokeRequest);
				}
			}

		} else {

			if (null == bodyMessage || StrUtil.isBlank(bodyMessage.getContent())) {
				return;
			}

			StringBuilder stringBuilder = new StringBuilder("群组:");
			stringBuilder.append(headMessage.getGroupInfo().getGroupName())
					.append(",")
					.append(sendUserName)
					.append(":")
					.append(JSONObject.toJSONString(bodyMessage));

			System.err.println(stringBuilder.toString());

			Integer subMsgType = bodyMessage.getSubMsgType();

			if (subMsgType.equals(0)) {// 单一或复合类型消息(文字 At 图片 语音自由组合)
				String message = bodyMessage.getContent();

				AtUin atUin = new AtUin();
				atUin.setNick(sendUserName);
				atUin.setUin(sendUin);

				String[] split = message.split(" ");

				if ("开启".equals(split[0]) || "关闭".equals(split[0])) {
					String[] admins = qqGroupConfig.getAdmin().split(",");
					if (!ArrayUtil.contains(admins, sendUin.toString())) {// 验证是否是管理员
						MessageQueue messageQueue = new MessageQueue();
						messageQueue.setToUin(fromUin);
						messageQueue.setToType(2);
						messageQueue.setAtUin(atUin);
						messageQueue.setMessage(BotMessage.NOT_ADMIN);

						QQUtil.sendMessage(messageQueue);
						return;
					}
					int state = "开启".equals(split[0]) ? 1 : 0;
					setFunction(fromUin, 2, state, split[1]);
				} else if ("查询".equals(split[0])) {
					String localPicNum = RandomPicUtil.getLocalPicNum();

					MessageQueue messageQueue = new MessageQueue();
					messageQueue.setToType(2);
					messageQueue.setToUin(fromUin);
					messageQueue.setAtUin(atUin);
					messageQueue.setMessage(localPicNum);

					SystemConfig.MessageQueues.put(System.currentTimeMillis(), messageQueue);

				} else if ("语音".equals(split[0])) {
					// 发送语音消息
				} else {
					sendGroupPic(qqGroupConfig, message, fromUin, atUin);
				}
			} else if (subMsgType.equals(12)) {// Xml消息

			} else if (subMsgType.equals(19)) {// Video消息

			} else if (subMsgType.equals(51)) {// JSON卡片消息

			}
		}
	}

	/**
	 * 进群事件
	 * 
	 * @param headMessage
	 * @param bodyMessage
	 */
	public static void groupJoin(HeadMessage headMessage, HeadEvent headEvent) {
		// 获取消息来源
		Long fromUin = headMessage.getFromUin();

		QQGroupConfig qqGroupConfig = SystemConfig.GROUP_LIST.stream()
				.filter(config -> config.getGroupUin().equals(fromUin))
				.findFirst()
				.orElse(null);

		if (null == qqGroupConfig || qqGroupConfig.getGroupJoin().equals(0)) {// 没有该群的配置
			return;
		}

		// 获取发消息的对象
		Long sendUin = headMessage.getSenderUin();
		if (BOT.equals(sendUin)) {// 机器人的消息不做处理
			return;
		}

		QQGroupInfo qqGroupInfo = QQUtil.getUinByUid(headEvent.getUid());

		if (null == qqGroupInfo) {
			return;
		}

		AtUin atUin = new AtUin();
		atUin.setNick(qqGroupInfo.getNick());
		atUin.setUin(sendUin);

		MessageQueue messageQueue = new MessageQueue();
		messageQueue.setToType(2);
		messageQueue.setToUin(fromUin);
		messageQueue.setAtUin(atUin);
		messageQueue.setMessage(BotMessage.GROUP_JOIN);
		QQUtil.sendMessage(messageQueue);

		// 添加群组用户信息
		GroupUserInfoService userInfoService = ApplicationContextHelper.getBean(GroupUserInfoService.class);
		GroupUserInfo groupUserInfo = new GroupUserInfo();
		groupUserInfo.setGroupUin(fromUin);
		groupUserInfo.setJoinTime(new Date());
		groupUserInfo.setNickName(qqGroupInfo.getNick());
		groupUserInfo.setUid(qqGroupInfo.getUid());
		groupUserInfo.setUin(qqGroupInfo.getUin());
		groupUserInfo.setLevel(qqGroupInfo.getLevel());

		userInfoService.save(groupUserInfo);

	}

	/**
	 * 退群事件
	 * 
	 * @param headMessage
	 * @param bodyMessage
	 */
	public static void groupExit(HeadMessage headMessage, HeadEvent headEvent) {
		// 获取消息来源
		Long fromUin = headMessage.getFromUin();

		QQGroupConfig qqGroupConfig = SystemConfig.GROUP_LIST.stream()
				.filter(config -> config.getGroupUin().equals(fromUin))
				.findFirst()
				.orElse(null);

		if (null == qqGroupConfig || qqGroupConfig.getGroupExit().equals(0)) {// 没有该群的配置
			return;
		}

		// 获取发消息的对象
		Long sendUin = headMessage.getSenderUin();
		if (BOT.equals(sendUin)) {// 机器人的消息不做处理
			return;
		}

		QQGroupInfo qqGroupInfo = QQUtil.getUinByUid(headEvent.getUid());

		MessageQueue messageQueue = new MessageQueue();
		messageQueue.setToType(2);
		messageQueue.setToUin(fromUin);
		messageQueue.setMessage(qqGroupInfo.getNick() + BotMessage.GROUP_EXIT);
		QQUtil.sendMessage(messageQueue);
	}

	/**
	 * 设置QQ群功能
	 * 
	 * @param fromUin  QQ群
	 * @param toType   1好友/2群组
	 * @param state    状态 0关闭/1开启
	 * @param function 功能名称
	 */
	private static String setFunction(Long fromUin, Integer toType, int state, String function) {
		QQGroupConfig updateConfig = new QQGroupConfig();
		updateConfig.setGroupUin(fromUin);

		MessageQueue messageQueue = new MessageQueue();
		messageQueue.setToType(toType);
		messageQueue.setToUin(fromUin);
		messageQueue.setMessage(function + (state == 1 ? BotMessage.OPEN : BotMessage.CLOSE));

		if ("GPT".equalsIgnoreCase(function)) {
			updateConfig.setGpt(state);
			refreshGroupConfig(updateConfig);
		} else if ("r18".equalsIgnoreCase(function)) {
			updateConfig.setR18(state);
			refreshGroupConfig(updateConfig);
		} else if ("旋转".equalsIgnoreCase(function)) {
			updateConfig.setRotate(state);
			refreshGroupConfig(updateConfig);
		} else if ("看图".equalsIgnoreCase(function)) {
			updateConfig.setPic(state);
			updateConfig.setSetuStatistics(state);
			refreshGroupConfig(updateConfig);
		} else {
			messageQueue.setMessage(BotMessage.NO_FUNCTION);
		}

		SystemConfig.MessageQueues.put(System.currentTimeMillis(), messageQueue);

		return messageQueue.getMessage();
	}

	/**
	 * 刷新QQ群组配置
	 */
	private static void refreshGroupConfig(QQGroupConfig groupConfig) {
		QQGroupConfigService qqGroupConfigService = ApplicationContextHelper.getBean(QQGroupConfigService.class);

		qqGroupConfigService.update(Wrappers.lambdaUpdate(new QQGroupConfig())
				.set(groupConfig.getR18() != null, QQGroupConfig::getR18, groupConfig.getR18())
				.set(groupConfig.getGpt() != null, QQGroupConfig::getGpt, groupConfig.getGpt())
				.set(groupConfig.getPic() != null, QQGroupConfig::getPic, groupConfig.getPic())
				.set(groupConfig.getSetuStatistics() != null, QQGroupConfig::getSetuStatistics,
						groupConfig.getSetuStatistics())
				.set(groupConfig.getRotate() != null, QQGroupConfig::getRotate, groupConfig.getRotate())
				.eq(QQGroupConfig::getGroupUin, groupConfig.getGroupUin()));

		SystemConfig.GROUP_LIST.clear();
		SystemConfig.GROUP_LIST.addAll(qqGroupConfigService.list());
	}

	/**
	 * 发送图片
	 * 
	 * @param qqGroupConfig
	 * @param message       消息内容
	 * @param fromUin       群QQ
	 * @param sendUserName  回复人的昵称
	 * @param sendUin       回复人的QQ
	 */
	private static void sendGroupPic(QQGroupConfig qqGroupConfig, String message, Long fromUin, AtUin atUin) {
		Pattern compile = Pattern.compile("来(.*?)[点丶、个份张幅](.*?)的?([rR]18)?[色瑟涩䔼😍🐍][图圖🤮]");
		Matcher matcher = compile.matcher(message);

		if (!matcher.find()) {
			return;
		}

		MessageQueue messageQueue = new MessageQueue();
		messageQueue.setAtUin(atUin);
		messageQueue.setToUin(fromUin);
		messageQueue.setToType(2);

		int hour = LocalDateTime.now().getHour();
		if (hour <= 6) {
			messageQueue.setMessage("这么晚还不睡，丫丫都被你吵醒了，不开心 ￣へ￣");
			SystemConfig.MessageQueues.put(System.currentTimeMillis(), messageQueue);
			return;
		}

		if (qqGroupConfig.getPic().equals(0)) {
			messageQueue.setMessage("\n\n看图" + BotMessage.NOT_OPEN);
			SystemConfig.MessageQueues.put(System.currentTimeMillis(), messageQueue);
			return;
		}

		// 数量
		String group = matcher.group(1);
		Integer integer = DigitalConversionDict.dict.get(group);
		int num = integer == null ? 1 : integer;

		// 群速率限制
		RedisService redisService = ApplicationContextHelper.getBean(RedisService.class);
		String key = SystemConfig.GROUP_PIC_RATE + fromUin;
		Integer count = (Integer) redisService.get(key);
		if (count == null) {
			redisService.set(key, num, 180);
		} else if ((count + num) <= qqGroupConfig.getPicRate()) {
			redisService.incr(key, num, 180);
		} else {
			long ttl = redisService.getTtl(key);
			messageQueue.setMessage(String.format("\n\n本群每%s秒能发%s，已发%s，离刷新还剩%sS，年轻人注意节制，冲太多对身体不好", 180,
					qqGroupConfig.getPicRate(), count, ttl));

			ArrayList<String> md5List = new ArrayList<String>();
			md5List.add(RandomPicUtil.getErrorPic());
			messageQueue.setImageMd5List(md5List);
			messageQueue.setCommandId(2);

			SystemConfig.MessageQueues.put(System.currentTimeMillis(), messageQueue);
			return;
		}

		// 标签
		String tag = matcher.group(2);

		// r18
		int r18 = "r18".equalsIgnoreCase(matcher.group(3)) ? 1 : 0;

		// 验证是否开启R18
		if (r18 == 1 && qqGroupConfig.getR18().equals(0)) {
			messageQueue.setMessage(BotMessage.NOT_OPEN_R18);
			SystemConfig.MessageQueues.put(System.currentTimeMillis(), messageQueue);
			return;
		}

		ArrayList<String> md5List = new ArrayList<>();

		for (int i = 0; i < num; i++) {
			String md5 = RandomPicUtil.randomPic(fromUin, qqGroupConfig.getRotate(), r18);
			if (StrUtil.isNotBlank(md5)) {
				md5List.add(md5);
			}
		}

		messageQueue.setImageMd5List(md5List);
		messageQueue.setCommandId(2);
		messageQueue.setRevoke(1);

		SystemConfig.MessageQueues.put(System.currentTimeMillis(), messageQueue);

		if (qqGroupConfig.getSetuStatistics().equals(1)) {// 开启了涩图统计
			// 计数
			String userImgCountKey = SystemConfig.GROUP_USER_SENDIMG_COUNT + fromUin + ":" + atUin.getUin();
			redisService.incr(userImgCountKey, 1);
		}
	}

}
