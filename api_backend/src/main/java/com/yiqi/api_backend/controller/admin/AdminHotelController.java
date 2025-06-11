package com.yiqi.api_backend.controller.admin;


import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiqi.api_backend.common.base.BaseController;
import com.yiqi.api_backend.common.dictionary.HotelDict;
import com.yiqi.api_backend.common.lang.Result;
import com.yiqi.api_backend.entity.Hotel;
import com.yiqi.api_backend.service.HotelService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@SaCheckRole(type = "admin", value = "ADMIN")
@RestController
@RequestMapping("/admin/hotel")
public class AdminHotelController extends BaseController {

	@Resource
	HotelService hotelService;

	@GetMapping("/page")
	public Result page(Hotel hotel) {
		Page<Hotel> pageData = hotelService.page(getPage(), queryLike(hotel));
		return Result.success(pageData);
	}

	@GetMapping("/info/{id}")
	public Result info(@PathVariable long id) {
		Hotel hotel = hotelService.getById(id);

		return Result.success(MapUtil.builder()
				.put("hotel", hotel)
				.put("rankDict", HotelDict.getMapList(HotelDict.rankDict))
				.build());
	}

	@PostMapping("/save")
	public Result save(@Validated @RequestBody Hotel hotel) {
		if (hotel.getId() == null) {
			hotel.setCreateTime(LocalDateTime.now());
		}
		hotelService.saveOrUpdate(hotel);
		return Result.success();
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody Long[] ids) {
		Assert.isTrue(ArrayUtil.isNotEmpty(ids), "请选择删除选项");
		hotelService.removeBatchByIds(List.of(ids));
		return Result.success();
	}
}
