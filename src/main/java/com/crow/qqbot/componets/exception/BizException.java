package com.crow.qqbot.componets.exception;

/**
 * <p>
 * 业务异常类信息
 * </p>
 * 
 * @author Crow
 * @version 0.0.1
 */
public class BizException extends RuntimeException {
	private static final long serialVersionUID = -7864604160297181941L;

	/** 错误码 */
	protected final ErrorCode errorCode;

	/**
	 * 无参默认构造UNSPECIFIED
	 */
	public BizException() {
		super(BizErrorCode.UNSPECIFIED.getDescription());
		this.errorCode = BizErrorCode.UNSPECIFIED;
	}

	/**
	 * 指定错误码构造通用异常
	 * 
	 * @param errorCode 错误码
	 */
	public BizException(final ErrorCode errorCode) {
		super(errorCode.getDescription());
		this.errorCode = errorCode;
	}

	/**
	 * 指定详细描述构造通用异常
	 * 
	 * @param detailedMessage 详细描述
	 */
	public BizException(final String detailedMessage) {
		super(detailedMessage);
		this.errorCode = BizErrorCode.UNSPECIFIED;
	}

	/**
	 * 指定导火索构造通用异常
	 * 
	 * @param t 导火索
	 */
	public BizException(final Throwable t) {
		super(t);
		this.errorCode = BizErrorCode.UNSPECIFIED;
	}

	/**
	 * 构造通用异常
	 * 
	 * @param errorCode       错误码
	 * @param detailedMessage 详细描述
	 */
	public BizException(final ErrorCode errorCode, final String detailedMessage) {
		super(detailedMessage);
		this.errorCode = errorCode;
	}

	/**
	 * 构造通用异常
	 * 
	 * @param errorCode 错误码
	 * @param t         导火索
	 */
	public BizException(final ErrorCode errorCode, final Throwable t) {
		super(errorCode.getDescription(), t);
		this.errorCode = errorCode;
	}

	/**
	 * 构造通用异常
	 * 
	 * @param detailedMessage 详细描述
	 * @param t               导火索
	 */
	public BizException(final String detailedMessage, final Throwable t) {
		super(detailedMessage, t);
		this.errorCode = BizErrorCode.UNSPECIFIED;
	}

	/**
	 * 构造通用异常
	 * 
	 * @param errorCode       错误码
	 * @param detailedMessage 详细描述
	 * @param t               导火索
	 */
	public BizException(final ErrorCode errorCode, final String detailedMessage, final Throwable t) {
		super(detailedMessage, t);
		this.errorCode = errorCode;
	}

	/**
	 * 获取错误码
	 * 
	 * @return
	 */
	public int getErrorCode() {
		return errorCode.getCode();
	}

}
