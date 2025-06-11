package com.yiqi.api_backend.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiqi.api_backend.common.dto.LoginDto;
import com.yiqi.api_backend.common.dto.RegisterDto;
import com.yiqi.api_backend.common.dto.UserDto;
import com.yiqi.api_backend.common.lang.Const;
import com.yiqi.api_backend.entity.User;
import com.yiqi.api_backend.mapper.UserMapper;
import com.yiqi.api_backend.security.StpKit;
import com.yiqi.api_backend.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
		implements UserService {

	@Override
	public UserDto login(LoginDto dto) {
		User user = this.getOne(new QueryWrapper<User>().eq("username", dto.getUsername()));
		Assert.notNull(user, "用户名或密码错误");
		Assert.isTrue(user.getPassword().equals(DigestUtil.sha256Hex(dto.getPassword())), "用户名或密码错误");

		user.setLastLoginTime(LocalDateTime.now());
		this.updateById(user);

		return BeanUtil.toBean(user, UserDto.class);
	}

	@Override
	public void register(RegisterDto dto) {

		boolean exists = this.exists(new QueryWrapper<User>().eq("username", dto.getUsername()));
		Assert.isTrue(!exists, "用户名已存在，请更换");

		User user = new User();
		user.setUsername(dto.getUsername());
		user.setPassword(DigestUtil.sha256Hex(dto.getPassword()));
		user.setCreateTime(LocalDateTime.now());
		user.setAvatar(Const.DEFAULT_AVATAR);
		this.save(user);
	}

	@Override
	public UserDto updateInfo(User user) {
		// 新输入的用户名不能和别人的重复
		boolean exists = this.exists(new QueryWrapper<User>().eq("username", user.getUsername()).ne("id", user.getId()));
		Assert.isTrue(!exists, "用户名已存在，请更换");

		// 不能修改别人的用户信息
		Assert.isTrue(user.getId().longValue() == StpKit.getUserId(), "无权限操作");

		User temp = this.getById(user.getId());
		temp.setUsername(user.getUsername());
		temp.setAvatar(user.getAvatar());
		temp.setSignature(user.getSignature());
		temp.setPhone(user.getPhone());

		this.updateById(temp);

		return BeanUtil.toBean(temp, UserDto.class);
	}
}




