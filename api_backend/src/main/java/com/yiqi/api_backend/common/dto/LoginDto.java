package com.yiqi.api_backend.common.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 登录参数
 */
@Data
public class LoginDto implements Serializable {

	@Serial
	private static final long serialVersionUID = 1302977055310537554L;

	@NotBlank(message = "用户名不能为空")
	private String username;

	@NotBlank(message = "密码不能为空")
	private String password;

}
