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
import com.yiqi.api_backend.entity.Room;
import com.yiqi.api_backend.security.StpKit;
import com.yiqi.api_backend.service.RoomService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/room")
public class AdminRoomController extends BaseController {

	@Resource
	RoomService roomService;

	@GetMapping("/page")
	public Result page(String hotelTitle) {
		QueryWrapper<Room> wrapper = new QueryWrapper<>();
		wrapper.orderByDesc("create_time");
		wrapper.like(StrUtil.isNotBlank(hotelTitle), "h.title", hotelTitle);

		Page<Room> pageData = roomService.getPage(getPage(), wrapper);
		return Result.success(pageData);
	}

	@GetMapping("/info/{id}")
	public Result info(@PathVariable long id) {
        Room room =roomService.getById(id);
		return Result.success(room);
	}

	@SaCheckRole(type = "admin", value = "HOTEL")
	@PostMapping("/save")
	public Result save(@Validated @RequestBody Room room) {
		if (room.getId() == null) {
            room.setCreateTime(LocalDateTime.now());
		}
		room.setHotelId(Convert.toLong(StpKit.ADMIN.getExtra("hotelId")));
		roomService.saveOrUpdate(room);
		return Result.success();
	}

	@SaCheckRole(type = "admin", value = "HOTEL")
	@PostMapping("/delete")
	public Result delete(@RequestBody Long[] ids) {
		Assert.isTrue(ArrayUtil.isNotEmpty(ids), "请选择删除选项");
		roomService.removeBatchByIds(List.of(ids));
		return Result.success();
	}
}
