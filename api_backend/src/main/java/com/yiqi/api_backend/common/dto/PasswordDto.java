package com.yiqi.api_backend.common.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;


/**
 * 修改密码参数
 */
@Data
public class PasswordDto implements Serializable {

	@Serial
	private static final long serialVersionUID = -8182775071480408588L;

	@NotBlank(message = "旧密码不能为空")
	private String oldPass;

	@NotBlank(message = "新密码不能为空")
	@Length(min = 6, max = 16, message = "密码长度不能少于6位，不能大于16位")
	private String newPass;

}
