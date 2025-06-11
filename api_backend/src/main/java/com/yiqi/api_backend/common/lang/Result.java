package com.yiqi.api_backend.common.lang;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 封装API的返回对象
 */
@Data
public class Result implements Serializable {

	public static final int SUCCESS_CODE = 200;
	public static final int FAIL_CODE = 500;
	@Serial
	private static final long serialVersionUID = 2241014268061545241L;

	private int code;
	private String msg;
	private Object data;

	public static Result success() {
		return instance(SUCCESS_CODE, "操作成功", null);
	}

	public static Result success(Object data) {
		return instance(SUCCESS_CODE, "操作成功", data);
	}

	public static Result fail(String msg) {
		return instance(FAIL_CODE, msg, null);
	}

	public static Result instance(int code, String msg, Object data) {
		Result result = new Result();
		result.setCode(code);
		result.setMsg(msg);
		result.setData(data);
		return result;
	}

}
