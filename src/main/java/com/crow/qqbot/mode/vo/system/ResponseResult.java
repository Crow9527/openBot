package com.crow.qqbot.mode.vo.system;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * </p>
 * 
 * @author Crow
 * @version 0.0.1
 * @since 2020年7月31日下午4:56:55
 */
@Getter
@Setter
public class ResponseResult {

	private Object data;

	private int code = 0;

	private String message;

	public ResponseResult(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	/**
	 * 返回正确结果
	 * 
	 * @param message
	 * @return
	 */
	public static ResponseResult success(Object data) {
		return new ResponseResult(0, "", data);
	}

	/**
	 * 返回正确结果
	 * 
	 * @return
	 */
	public static ResponseResult success() {
		return new ResponseResult(0, "", null);
	}

	/**
	 * 返回正确结果
	 * 
	 * @param message
	 * @return
	 */
	public static ResponseResult success(String message, Object data) {
		return new ResponseResult(0, message, data);
	}

	/**
	 * 返回成功信息
	 * 
	 * @param message
	 * @return
	 */
	public static ResponseResult message(String message) {
		return new ResponseResult(0, message, "");
	}

	/**
	 * 返回成功信息
	 * 
	 * @param message
	 * @return
	 */
	public static ResponseResult message(int code, String message) {
		return new ResponseResult(code, message, "");
	}

	/**
	 * 
	 * 返回错误信息
	 * 
	 * @param message
	 * @return
	 */
	public static ResponseResult fail(String message) {
		return new ResponseResult(500, message, "");
	}

	/**
	 * 
	 * 返回错误信息
	 * 
	 * @param message
	 * @return
	 */
	public static ResponseResult fail(int code, String message) {
		return new ResponseResult(code, message, "");
	}

}
