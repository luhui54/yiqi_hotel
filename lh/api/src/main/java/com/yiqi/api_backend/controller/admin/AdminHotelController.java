package com.markerhub.controller.admin;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.common.base.BaseController;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Hotel;
import com.markerhub.service.HotelService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
        Hotel hotel =hotelService.getById(id);
		return Result.success(hotel);
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
