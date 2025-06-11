package com.markerhub.controller.admin;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.common.base.BaseController;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Room;
import com.markerhub.service.RoomService;
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
	public Result page(Room room) {
		Page<Room> pageData = roomService.page(getPage(), queryLike(room));
		return Result.success(pageData);
	}

	@GetMapping("/info/{id}")
	public Result info(@PathVariable long id) {
        Room room =roomService.getById(id);
		return Result.success(room);
	}

	@PostMapping("/save")
	public Result save(@Validated @RequestBody Room room) {
		if (room.getId() == null) {
            room.setCreateTime(LocalDateTime.now());
		}
		roomService.saveOrUpdate(room);
		return Result.success();
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody Long[] ids) {
		Assert.isTrue(ArrayUtil.isNotEmpty(ids), "请选择删除选项");
		roomService.removeBatchByIds(List.of(ids));
		return Result.success();
	}
}
