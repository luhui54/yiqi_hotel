package com.yiqi.api_backend.common.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;


/**
 * 注册参数
 */
@Data
public class RegisterDto implements Serializable {

	@Serial
	private static final long serialVersionUID = -6143649323172438717L;

	@NotBlank(message = "用户名不能为空")
	private String username;

	@NotBlank(message = "密码不能为空")
	@Length(min = 6, max = 16, message = "密码长度不能少于6位，不能大于16位")
	private String password;

	@NotBlank(message = "验证码不能为空")
	private String code;

	@NotBlank(message = "验证码不能为空")
	private String uuid;

}
