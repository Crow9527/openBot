package com.crow.qqbot.componets.constant;

/**
 * <p>
 * QQ消息事件
 * </p>
 * 
 * @author crow
 * @since 2023年8月4日 上午10:38:50
 */
public class QQEventConstant {

	/**
	 * 收到好友/私聊消息事件
	 */
	public static final String ON_EVENT_FRIEND_NEW_MSG = "ON_EVENT_FRIEND_NEW_MSG";

	/**
	 * 收到群组消息事件
	 */
	public static final String ON_EVENT_GROUP_NEW_MSG = "ON_EVENT_GROUP_NEW_MSG";

	/**
	 * 群组系统消息事件
	 */
	public static final String ON_EVENT_GROUP_SYSTEM_MSG_NOTIFY = "ON_EVENT_GROUP_SYSTEM_MSG_NOTIFY";

	/**
	 * 群组邀请事件
	 */
	public static final String ON_EVENT_GROUP_INVITE = "ON_EVENT_GROUP_INVITE";

	/**
	 * 退群邀请事件
	 */
	public static final String ON_EVENT_GROUP_EXIT = "ON_EVENT_GROUP_EXIT";

	/**
	 * 进群事件
	 */
	public static final String ON_EVENT_GROUP_JOIN = "ON_EVENT_GROUP_JOIN";

}
