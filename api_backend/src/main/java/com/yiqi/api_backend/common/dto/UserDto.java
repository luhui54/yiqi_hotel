package com.yiqi.api_backend.common.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 用户dto
 */
@Data
public class UserDto implements Serializable {

	@Serial
	private static final long serialVersionUID = 5872818929723339074L;

	/**
	 * 用户id
	 */
	private Long id;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 角色
	 */
	private String role;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

}
