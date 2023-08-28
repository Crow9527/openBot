package com.crow.qqbot.componets.exception;

/**
 * <p>
 * 业务错误码
 * </p>
 * 
 * @author Crow
 * @version 0.0.1
 */
public enum BizErrorCode implements ErrorCode {
	/** 未指明的异常 */
	UNSPECIFIED(500, "服务器繁忙，请稍后再试"), 
	NO_SERVICE(404, "访问资源不存在"),

	
	STATUS_ERROR(800,"状态错误"),
	USER_NON_EXISTENT(801,"用户不存在"),
	
	// 登录异常
	LOGIN_ERROR(900, "请登录"), 
	LOGIN_OVERTIME_ERROR(901, "登录超时，请重新登录"), 
	LOGIN_OTHER_PLACE_ERROR(902, "账号已在其他地点登陆，请重新登录"),
	LOGIN_FORCE(903, "该账户已在其他地点登录，是否强制登录"),
	LOGIN_ROLE_CHANGE(904, "角色变更，请重新登录"),
	
	//操作异常
	JURISDICTION_ERROR(701, "无权操作"),
	
	// 通用异常
	ACCESSLIMIT_ERROR(1000, "操作过于频繁"),
	FILE_EXIST_ERROE(1001,"文件不存在"),
	VIOLATION_PARAMETER_ERROR(1002,"您所访问的页面请求中有违反安全规则元素存在，拒绝访问"),
	METHOD_PARAMETER_TYPE_MISMATCH(1003,"方法参数类型不匹配"),
	REQUEST_METHOD_NOT_SUPPORTED(1004,"不支持的请求方式"),
	MISSING_SERVLET_REQUEST_PARAMETER(1005,"缺少请求参数"),
	DATA_PARSING_ERROR(1008,"数据解析错误"),
	INTERFACE_DISABLED_ERROR(1011,"系统维护中，终止访问")
	;

	/**
	 * 错误码
	 */
	private final int code;

	/**
	 * 描述
	 */
	private final String description;

	/**
	 * @param code        错误码
	 * @param description 描述
	 */
	private BizErrorCode(final int code, final String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * 根据编码查询枚举。
	 *
	 * @param code 编码。
	 * @return 枚举。
	 */
	public static BizErrorCode getByCode(int code) {
		for (BizErrorCode value : BizErrorCode.values()) {
			if (code == value.getCode()) {
				return value;
			}
		}
		return UNSPECIFIED;
	}

	/**
	 * 枚举是否包含此code
	 * 
	 * @param code 枚举code
	 * @return 结果
	 */
	public static Boolean contains(int code) {
		for (BizErrorCode value : BizErrorCode.values()) {
			if (code == value.getCode()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	public static void main(String[] args) {
		String code="New_role_permission_exceeds_current_user_role_permission";
		System.out.println(code.toUpperCase());
	}
}
