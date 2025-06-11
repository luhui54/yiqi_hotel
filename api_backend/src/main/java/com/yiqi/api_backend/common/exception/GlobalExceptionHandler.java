package com.yiqi.api_backend.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.yiqi.api_backend.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = BizException.class)
	public Result handler(BizException e) {
		log.error("自定义业务异常 ----------- {}", e.getMsg());
		return Result.instance(e.getCode(), e.getMsg(), null);
	}

	@ExceptionHandler(value = BindException.class)
	public Result handler(BindException e) {
		ObjectError error = e.getBindingResult().getAllErrors().stream().findFirst().get();
		return Result.fail(error.getDefaultMessage());
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	public Result handler(IllegalArgumentException e) {
		return Result.fail(e.getMessage());
	}

	@ExceptionHandler(value = NotLoginException.class)
	public Result handler(NotLoginException e) {
		return Result.instance(401, "请先登录", null);
	}

	@ExceptionHandler(value = NotPermissionException.class)
	public Result handler(NotPermissionException e) {
		return Result.instance(403, "无权限操作,请联系管理员", null);
	}

	@ExceptionHandler(value = NotRoleException.class)
	public Result handler(NotRoleException e) {
		return Result.instance(403, "无权限操作,请联系后台管理员！", null);
	}

	@ExceptionHandler(value = RuntimeException.class)
	public Result handler(RuntimeException e) {
		log.error("运行时异常 ----------- {}", e.getMessage());
		e.printStackTrace();
		return Result.fail(e.getMessage() == null || e.getMessage().length() > 15 ? "系统内部服务异常" : e.getMessage());
	}


}
