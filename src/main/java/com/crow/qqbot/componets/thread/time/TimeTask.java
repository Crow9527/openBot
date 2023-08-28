package com.crow.qqbot.componets.thread.time;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.crow.qqbot.componets.config.SystemConfig;
import com.crow.qqbot.componets.constant.BotMessage;
import com.crow.qqbot.mode.bean.qq.GroupUserInfo;
import com.crow.qqbot.mode.bean.qq.QQGroupConfig;
import com.crow.qqbot.mode.vo.qq.GroupUserPicCount;
import com.crow.qqbot.mode.vo.qq.Image;
import com.crow.qqbot.mode.vo.qq.MessageQueue;
import com.crow.qqbot.mode.vo.qq.QQResponse;
import com.crow.qqbot.mode.vo.qq.RevokeRequest;
import com.crow.qqbot.mode.vo.qq.Voice;
import com.crow.qqbot.service.qq.GroupUserInfoService;
import com.crow.qqbot.service.qq.QQGroupConfigService;
import com.crow.qqbot.service.system.RedisService;
import com.crow.qqbot.mode.vo.qq.QQResponse.ResponseData;
import com.crow.qqbot.utils.QQUtil;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.log4j.Log4j2;

/**
 * <p>
 * </p>
 * 
 * @author Crow
 * @version 0.0.1
 * @since 2019年11月21日上午10:59:08
 */
@Log4j2
@Component
public class TimeTask {

	@Resource
	private QQGroupConfigService groupConfigService;

	@Resource
	private RedisService redisService;

	@Resource
	private GroupUserInfoService userInfoService;

	/**
	 * 定时撤回消息
	 */
	@Scheduled(fixedDelay = 2000)
	public void revokeMessageTask() {
		Iterator<Entry<Long, RevokeRequest>> iterator = SystemConfig.REVOKE_MESSAGE_MAP.entrySet().iterator();
		while (iterator.hasNext()) {
			RevokeRequest value = iterator.next().getValue();

			Date date = new Date();
			if (null == value.getSendDate() || DateUtil.isIn(date, value.getSendDate(), value.getRevokeDate())) {
				continue;
			}

			// 撤回消息
			QQResponse revokeMessage = QQUtil.revokeGroupMessage(value.getUin(), value.getMsgSeq(),
					value.getMsgRandom());
			if (null != revokeMessage && revokeMessage.getCgiBaseResponse().getRet().equals(0)) {
				iterator.remove();
			}
			log.info("撤回消息:[{}],撤回消息响应:[{}]", value.toString(), JSONObject.toJSONString(revokeMessage));
		}
	}

	/**
	 * 定时消费消息
	 */
	@Scheduled(fixedDelay = 1000)
	public void sendMessageTask() {
		Iterator<Entry<Long, MessageQueue>> iterator = SystemConfig.MessageQueues.entrySet().iterator();
		while (iterator.hasNext()) {
			MessageQueue messageQueue = iterator.next().getValue();
			try {

				// 存在图片
				if (CollectionUtil.isNotEmpty(messageQueue.getImageMd5List())) {
					ArrayList<Image> imageList = new ArrayList<>();
					for (String md5 : messageQueue.getImageMd5List()) {
						// 上传图片
						QQResponse qqResponse = QQUtil.upload(messageQueue.getCommandId(), md5);
						if (null != qqResponse && qqResponse.getCgiBaseResponse().getRet().equals(0)) {
							ResponseData responseData = qqResponse.getResponseData();
							Image image = new Image();
							image.setFileId(responseData.getFileId());
							image.setFileMd5(responseData.getFileMd5());
							image.setFileSize(responseData.getFileSize());
							imageList.add(image);
						} else {
							messageQueue.setMessage("\n\n获取失败");
						}
					}
					messageQueue.setImageList(imageList);
				}

				// 存在语音
				if (StrUtil.isNotBlank(messageQueue.getVoiceMd5())) {
					// 上传语音
					QQResponse qqResponse = QQUtil.upload(messageQueue.getCommandId(), messageQueue.getVoiceMd5());
					if (null != qqResponse && qqResponse.getCgiBaseResponse().getRet().equals(0)) {
						ResponseData responseData = qqResponse.getResponseData();
						Voice voice = new Voice();
						voice.setFileToken(responseData.getFileToken());
						voice.setFileMd5(responseData.getFileMd5());
						voice.setFileSize(responseData.getFileSize());
						messageQueue.setVoice(voice);
					}
				}

				// 添加要撤回消息标识
				if (messageQueue.getRevoke().equals(1)) {
					messageQueue.setMessage(SystemConfig.REVOKE_MARK);
				}

				// 发送消息
				QQUtil.sendMessage(messageQueue);

				// 每次发送消息间隔1100
				Thread.sleep(1100);

			} catch (Exception e) {
				messageQueue.setMessage(BotMessage.SYSTEM_ERROR);
				QQUtil.sendMessage(messageQueue);
			}

			iterator.remove();
		}
	}

	/**
	 * 随机事件 每整点执行一次
	 */
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void randomEvent() {

		LocalDateTime now = LocalDateTime.now();

		int dayOfWeek = now.getDayOfWeek().getValue();
		if (dayOfWeek >= 6) { // 周六周日休息
			return;
		}

		if (now.getHour() == 18 && now.getMinute() < 10) {// 下班事件
			List<QQGroupConfig> list = groupConfigService.list();
			for (QQGroupConfig qqGroupConfig : list) {
				if (qqGroupConfig.getRandomEvent().equals(1)) {
					MessageQueue messageQueue = new MessageQueue();
					messageQueue.setToUin(qqGroupConfig.getGroupUin());
					messageQueue.setToType(2);
					messageQueue.setMessage("打卡下班~ ~ ~");
					QQUtil.sendMessage(messageQueue);
				}
			}
		}
	}

	/**
	 * 涩图统计排行 每天凌晨0点统计
	 */
	@Scheduled(cron = "0 1 0 * * ?")
	public void setuStatistics() {

		ArrayList<GroupUserPicCount> counts = new ArrayList<GroupUserPicCount>();

		Set<String> likeKeys = redisService.getLikeKeys(SystemConfig.GROUP_USER_SENDIMG_COUNT + "*");
		likeKeys.forEach(key -> {
			GroupUserPicCount userPicCount = new GroupUserPicCount();
			String[] split = key.split(":");

			userPicCount.setGroupUin(new Long(split[4]));
			userPicCount.setUin(new Long(split[5]));
			userPicCount.setCount((Integer) redisService.get(key));

			counts.add(userPicCount);

			// 删除key
			redisService.remove(key);

		});

		if (CollectionUtil.isEmpty(counts)) {
			return;
		}

		// 按QQ群分组
		Map<Long, List<GroupUserPicCount>> collect = counts.stream()
				.collect(Collectors.groupingBy(GroupUserPicCount::getGroupUin));

		collect.forEach((group, infos) -> {

			// 按获取涩图数量进行排名
			List<GroupUserPicCount> sorted = infos.stream()
					.sorted(Comparator.comparing(GroupUserPicCount::getCount).reversed())
					.collect(Collectors.toList());

			StringBuffer message = new StringBuffer();
			String no1Name = null;
			message.append("----昨日ST获取排行----\n\n");
			for (int i = 0; i < 5; i++) {// 只获取前五名

				GroupUserPicCount groupUserPicCount = null;

				try {
					groupUserPicCount = sorted.get(i);
				} catch (Exception e) {

				}

				if (null == groupUserPicCount) {
					continue;
				}

				// 获取用户信息
				GroupUserInfo groupUserInfo = userInfoService.getOne(Wrappers.lambdaQuery(new GroupUserInfo())
						.eq(GroupUserInfo::getGroupUin, group)
						.eq(GroupUserInfo::getUin, groupUserPicCount.getUin()));

				if (null == groupUserInfo) {
					continue;
				}

				if (i == 0 && groupUserPicCount.getCount() >= 20) {
					no1Name = groupUserInfo.getNickName();
				}

				message.append("第")
						.append(i + 1)
						.append("名：")
						.append(groupUserInfo.getNickName())
						.append("，获取ST：")
						.append(groupUserPicCount.getCount())
						.append("\n\n");

			}

			if (StrUtil.isNotBlank(no1Name)) {
				message.append("恭喜").append(no1Name).append("获取老se批之王称号！！！");
			}

			MessageQueue messageQueue = new MessageQueue();
			messageQueue.setToType(2);
			messageQueue.setMessage(message.toString());
			messageQueue.setToUin(group);

			SystemConfig.MessageQueues.put(System.currentTimeMillis(), messageQueue);

		});

	}

}
