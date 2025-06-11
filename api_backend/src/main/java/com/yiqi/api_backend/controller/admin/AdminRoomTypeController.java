package com.yiqi.api_backend.controller.admin;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiqi.api_backend.common.base.BaseController;
import com.yiqi.api_backend.common.lang.Result;
import com.yiqi.api_backend.entity.RoomType;
import com.yiqi.api_backend.security.StpKit;
import com.yiqi.api_backend.service.RoomTypeService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/roomType")
public class AdminRoomTypeController extends BaseController {

	@Resource
	RoomTypeService roomTypeService;

	@GetMapping("/page")
	public Result page(String hotelTitle) {
		QueryWrapper<RoomType> wrapper = new QueryWrapper<>();
		wrapper.orderByDesc("create_time");
		wrapper.like(StrUtil.isNotBlank(hotelTitle), "h.title", hotelTitle);
		Page<RoomType> pageData = roomTypeService.getPage(getPage(), wrapper);
		return Result.success(pageData);
	}

	@SaCheckRole(type = "admin", value = "HOTEL")
	@GetMapping("/list")
	public Result list() {
		List<RoomType> roomTypes = roomTypeService.list();
		return Result.success(roomTypes);
	}

	@GetMapping("/info/{id}")
	public Result info(@PathVariable long id) {
		RoomType roomType = roomTypeService.getById(id);
		return Result.success(roomType);
	}

	@SaCheckRole(type = "admin", value = "HOTEL")
	@PostMapping("/save")
	public Result save(@Validated @RequestBody RoomType roomType) {
		if (roomType.getId() == null) {
			roomType.setCreateTime(LocalDateTime.now());
		}
		roomType.setHotelId(Convert.toLong(StpKit.ADMIN.getExtra("hotelId")));
		roomTypeService.saveOrUpdate(roomType);
		return Result.success();
	}

	@SaCheckRole(type = "admin", value = "HOTEL")
	@PostMapping("/delete")
	public Result delete(@RequestBody Long[] ids) {
		Assert.isTrue(ArrayUtil.isNotEmpty(ids), "请选择删除选项");
		roomTypeService.removeBatchByIds(List.of(ids));
		return Result.success();
	}
}
