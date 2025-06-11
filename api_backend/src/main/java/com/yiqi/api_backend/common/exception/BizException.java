package com.yiqi.api_backend.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 自定义500异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends RuntimeException{

	@Serial
	private static final long serialVersionUID = 852478716529955488L;
	private String msg;
	private int code = 500;

	public BizException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public BizException(int code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}
}
