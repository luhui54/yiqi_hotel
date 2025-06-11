package com.yiqi.api_backend.controller.sys;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiqi.api_backend.common.base.BaseController;
import com.yiqi.api_backend.common.lang.Result;
import com.yiqi.api_backend.entity.User;
import com.yiqi.api_backend.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {

	@Resource
	UserService userService;

	@GetMapping("/page")
	public Result page(User user) {
		Page<User> pageData = userService.page(getPage(), queryLike(user));
		return Result.success(pageData);
	}

	@GetMapping("/info/{id}")
	public Result info(@PathVariable long id) {
		User user = userService.getById(id);
		return Result.success(user);
	}
}
