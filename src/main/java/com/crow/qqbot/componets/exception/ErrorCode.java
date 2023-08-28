package com.crow.qqbot.componets.exception;

/**
 * <p>
 * 错误码接口
 * </p>
 * 
 * @author Crow
 * @version 0.0.1
 */
public interface ErrorCode {

	/**
	 * 获取错误码
	 * 
	 * @return
	 */
	int getCode();

	/**
	 * 获取错误信息
	 * 
	 * @return
	 */
	String getDescription();
}
