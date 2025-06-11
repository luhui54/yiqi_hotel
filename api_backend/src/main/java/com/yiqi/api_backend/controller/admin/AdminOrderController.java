package com.yiqi.api_backend.controller.admin;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiqi.api_backend.common.base.BaseController;
import com.yiqi.api_backend.common.lang.Result;
import com.yiqi.api_backend.entity.Order;
import com.yiqi.api_backend.entity.Room;
import com.yiqi.api_backend.entity.RoomType;
import com.yiqi.api_backend.service.OrderService;
import com.yiqi.api_backend.service.RoomService;
import com.yiqi.api_backend.service.RoomTypeService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin/order")
public class AdminOrderController extends BaseController {

	@Resource
	OrderService orderService;

	@Resource
	RoomService roomService;

	@Resource
	RoomTypeService typeService;

	@GetMapping("/page")
	public Result page(Order order) {
		QueryWrapper<Order> wrapper = new QueryWrapper<>();
		wrapper.orderByDesc("create_time");
		wrapper.eq(order.getSn() != null, "sn", order.getSn());
		wrapper.eq(order.getStatus() != null, "status", order.getStatus());

		Page<Order> pageData = orderService.page(getPage(), wrapper);
		return Result.success(pageData);
	}

	@GetMapping("/info/{id}")
	public Result info(@PathVariable long id) {
		Order order = orderService.getById(id);
		return Result.success(order);
	}

	/**
	 * 入住安排
	 */
	@PostMapping("/checkIn")
	@Transactional(rollbackFor = Exception.class)
	public Result checkIn(@RequestBody Order order) {
		Assert.notNull(order.getRoomId(), "请选择入住房间");

		Order temp = orderService.getById(order.getId());
		Assert.notNull(temp, "找不到订单记录");

		// 房间状态改为已入住
		Room room = roomService.getById(order.getRoomId());
		Assert.isTrue(room.getHotelId() == temp.getHotelId().longValue(), "操作异常");
		room.setStatus(1);
		roomService.updateById(room);

		temp.setRoomId(order.getRoomId());
		temp.setRoomSn(room.getSn());
		temp.setRealCheckInTime(LocalDateTime.now());
		temp.setStatus(3);

		orderService.saveOrUpdate(temp);

		return Result.success();
	}

	@PostMapping("/checkOut")
	@Transactional(rollbackFor = Exception.class)
	public Result checkOut(@NotNull Long id) {

		Order order = orderService.getById(id);
		Assert.notNull(order, "找不到订单记录");

		order.setStatus(4);
		order.setRealCheckOutTime(LocalDateTime.now());
		orderService.saveOrUpdate(order);

		// 释放房型库存
		RoomType roomType = typeService.getById(order.getRoomTypeId());
		roomType.setQuantity(roomType.getQuantity() + 1);
		typeService.updateById(roomType);

		// 房间状态改成空闲
		Room room = roomService.getById(order.getRoomId());
		room.setStatus(0);
		roomService.updateById(room);

		return Result.success();
	}

	@PostMapping("/cancel")
	public Result cancel(long id) {
		orderService.autoCancel(id);
		return Result.success();
	}
}
