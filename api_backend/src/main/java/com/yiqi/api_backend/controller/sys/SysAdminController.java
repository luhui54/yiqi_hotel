package com.yiqi.api_backend.controller.sys;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiqi.api_backend.common.base.BaseController;
import com.yiqi.api_backend.common.lang.Const;
import com.yiqi.api_backend.common.lang.Result;
import com.yiqi.api_backend.entity.Admin;
import com.yiqi.api_backend.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sys/admin")
public class SysAdminController extends BaseController {

	@Resource
	AdminService adminService;

	@GetMapping("/page")
	public Result page(Admin admin) {
		Page<Admin> pageData = adminService.getPage(getPage(), queryLike(admin));
		return Result.success(pageData);
	}

	@GetMapping("/info/{id}")
	public Result info(@PathVariable long id) {
		Admin admin = adminService.getById(id);
		return Result.success(admin);
	}

	@PostMapping("/save")
	public Result save(@Validated @RequestBody Admin admin) {
		if (admin.getId() == null) {
			admin.setCreateTime(LocalDateTime.now());
			if (admin.getAvatar() == null) {
				admin.setAvatar(Const.DEFAULT_AVATAR);
			}
			admin.setPassword(DigestUtil.sha256Hex("888888"));
			adminService.save(admin);
		} else {
			Admin temp = adminService.getById(admin.getId());
			BeanUtil.copyProperties(admin, temp, "password", "createTime");

			adminService.updateById(temp);
		}
		return Result.success();
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody Long[] ids) {
		Assert.isTrue(ArrayUtil.isNotEmpty(ids), "请选择删除选项");
		adminService.removeBatchByIds(List.of(ids));
		return Result.success();
	}

	@PostMapping("/resetPwd")
	public Result resetPwd(long id) {
		Admin admin = adminService.getById(id);
		Assert.notNull(admin, "管理员不存在");

		admin.setPassword(DigestUtil.sha256Hex("888888"));
		adminService.updateById(admin);
		return Result.success();
	}
}
