package com.yiqi.api_backend.controller.app;

import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.digest.DigestUtil;
import com.yiqi.api_backend.common.annotation.Login;
import com.yiqi.api_backend.common.dto.PasswordDto;
import com.yiqi.api_backend.common.lang.Result;
import com.yiqi.api_backend.entity.User;
import com.yiqi.api_backend.security.StpKit;
import com.yiqi.api_backend.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Login
@RestController
@RequestMapping("/app")
public class UserController {

	@Resource
	UserService userService;

	@GetMapping("/userInfo")
	public Result userInfo() {
		User user = userService.getById(StpKit.getUserId());
		return Result.success(user);
	}

	@PostMapping("/userInfo/update")
	public Result updateUserInfo(@Validated @RequestBody User user) {
		return Result.success(userService.updateInfo(user));
	}

	@PostMapping("/updatePass")
	public Result updatePass(@Validated @RequestBody PasswordDto dto) {
		User user = userService.getById(StpKit.getUserId());
		Assert.isTrue(user.getPassword().equals(DigestUtil.sha256Hex(dto.getOldPass())), "旧密码不正确");
		user.setPassword(DigestUtil.sha256Hex(dto.getNewPass()));
		userService.updateById(user);
		return Result.success();
	}
}