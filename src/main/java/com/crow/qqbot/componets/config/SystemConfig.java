package com.crow.qqbot.componets.config;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.crow.qqbot.mode.bean.qq.QQGroupConfig;
import com.crow.qqbot.mode.vo.qq.MessageQueue;
import com.crow.qqbot.mode.vo.qq.RevokeRequest;
import com.crow.qqbot.service.qq.QQGroupConfigService;

import lombok.extern.log4j.Log4j2;

/**
 * <p>
 * </p>
 *
 * @author Crow
 * @version 0.0.1
 * @since 2019年12月5日下午3:45:49
 */

@Log4j2
@Component
public class SystemConfig {

	@Resource
	private QQGroupConfigService qqGroupConfigService;

	/**
	 * 禁用接口List集合
	 */
	public static final String SYSTEM_PROHIBIT_INTERFACE_LIST = "SYSTEM:PROHIBIT_INTERFACE_LIST";

	/**
	 * 积分无效订单
	 */
	public static final String SYSTEM_INTEGRAL_INVALID = "SYSTEM:INTEGRAL:INVALID:";

	/**
	 * 积分完成订单
	 */
	public static final String SYSTEM_INTEGRAL_COMPLETE = "SYSTEM:INTEGRAL:COMPLETE:";

	/**
	 * 接口访问限制
	 */
	public static final String SYSTEM_ACCESS_LIMIT = "SYSTEM:ACCESS:LIMIT:";

	/**
	 * 签到状态
	 */
	public static final String WX_SIGN_STATUS = "SYSTEM:SIGN_STATUS:";

	/**
	 * 用户对话文本
	 */
	public static final String WX_CHAT_CONTEXT = "SYSTEM:CHAT_CONTEXT:";

	/**
	 * 手机号校验码
	 */
	public static final String PHONE_VERIFY_CODE = "SYSTEM:PHONE_VERIFY_CODE:";

	/**
	 * 用户注册积分数
	 */
	public static final String USER_REG_INTEGRAL = "SYSTEM:USER_REG_INTEGRAL";

	/**
	 * GPT模型列表
	 */
	public static final String GPT_MODEL_LIST = "GPT:MODEL:LIST";

	/**
	 * 用户请求频率
	 */
	public static final String GPT_REQUEST_FREQUENCY = "GPT:REQUEST:FREQUENCY:";

	/**
	 * GPT会话保存时间（6小时）
	 */
	public static final Integer GPT_SESSION_TIME = 21600;

	/**
	 * 系统代理
	 */
	public static final String SYSTEM_PROXY = "SYSTEM:PROXY";

	/**
	 * 群组已发送图片列表
	 */
	public static final String GROUP_SENDIMG = "GROUP:SENDIMG:";

	/**
	 * 群组用户发送图片计数
	 */
	public static final String GROUP_USER_SENDIMG_COUNT = "GROUP:USER:SENDIMG:COUNT:";

	/**
	 * 机器人群聊配置
	 */
	public static ArrayList<QQGroupConfig> GROUP_LIST = new ArrayList<>();

	/**
	 * 要撤回消息的标记
	 */
	public static final String REVOKE_MARK = "REVOKE";

	/**
	 * 要撤回消息的集合
	 */
	public static ConcurrentHashMap<Long, RevokeRequest> REVOKE_MESSAGE_MAP = new ConcurrentHashMap<Long, RevokeRequest>();

	/**
	 * 要发送的消息队列
	 */
	public static ConcurrentHashMap<Long, MessageQueue> MessageQueues = new ConcurrentHashMap<Long, MessageQueue>();

	/**
	 * 群组GPT请求频率
	 */
	public static final String GROUP_GPT_RATE = "GROUP:GPT:RATE:";

	/**
	 * 群组GPT会话历史
	 */
	public static final String GROUP_GPT_HISTORY = "GROUP:GPT:HISTORY:";

	/**
	 * 群组看图请求频率
	 */
	public static final String GROUP_PIC_RATE = "GROUP:PIC:RATE:";

	/**
	 * 在依赖注入完成后自动调用
	 */
	@PostConstruct
	private void createConfig() {
		GROUP_LIST.addAll(qqGroupConfigService.list());
		log.info("-------------------系统配置加载完毕---------------------------------------");
	}

}
