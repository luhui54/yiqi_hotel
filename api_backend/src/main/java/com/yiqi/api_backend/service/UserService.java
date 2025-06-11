package com.yiqi.api_backend.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yiqi.api_backend.common.dto.LoginDto;
import com.yiqi.api_backend.common.dto.RegisterDto;
import com.yiqi.api_backend.common.dto.UserDto;
import com.yiqi.api_backend.entity.User;

public interface UserService extends IService<User> {

	UserDto login(LoginDto dto);

	void register(RegisterDto dto);

	UserDto updateInfo(User user);
}
