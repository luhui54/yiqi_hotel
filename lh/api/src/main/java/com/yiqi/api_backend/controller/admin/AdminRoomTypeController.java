package com.markerhub.controller.admin;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.common.base.BaseController;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.RoomType;
import com.markerhub.service.RoomTypeService;
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
	public Result page(RoomType roomType) {
		Page<RoomType> pageData = roomTypeService.page(getPage(), queryLike(roomType));
		return Result.success(pageData);
	}

	@GetMapping("/info/{id}")
	public Result info(@PathVariable long id) {
        RoomType roomType =roomTypeService.getById(id);
		return Result.success(roomType);
	}

	@PostMapping("/save")
	public Result save(@Validated @RequestBody RoomType roomType) {
		if (roomType.getId() == null) {
            roomType.setCreateTime(LocalDateTime.now());
		}
		roomTypeService.saveOrUpdate(roomType);
		return Result.success();
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody Long[] ids) {
		Assert.isTrue(ArrayUtil.isNotEmpty(ids), "请选择删除选项");
		roomTypeService.removeBatchByIds(List.of(ids));
		return Result.success();
	}
}
