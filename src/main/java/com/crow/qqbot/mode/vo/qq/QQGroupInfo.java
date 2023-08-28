package com.crow.qqbot.mode.vo.qq;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * QQ群用户信息
 * </p>
 * 
 * @author crow
 * @since 2023年8月4日 下午7:56:49
 */
@Getter
@Setter
public class QQGroupInfo {

	private Long Uin;

	private String Uid;

	private String Nick;

	private String Head;

	private String Signature;

	private Integer Sex;

	private Integer Level;

}
