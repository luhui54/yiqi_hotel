package com.yiqi.api_backend.controller.app;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiqi.api_backend.common.annotation.Login;
import com.yiqi.api_backend.common.base.BaseController;
import com.yiqi.api_backend.common.dictionary.HotelDict;
import com.yiqi.api_backend.common.lang.OrderStatusConstants;
import com.yiqi.api_backend.common.lang.Result;
import com.yiqi.api_backend.entity.Order;
import com.yiqi.api_backend.security.StpKit;
import com.yiqi.api_backend.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Login
@RestController
@RequestMapping("/app/order")
public class OrderController extends BaseController {

	@Resource
	private OrderService orderService;

	@PostMapping("/create")
	public Result create(@RequestBody @Validated Order order) {
		return Result.success(orderService.create(order));
	}

	@GetMapping("/detail/{id}")
	public Result detail(@PathVariable long id) {
		return Result.success(orderService.getById(id));
	}

	@PostMapping("/cancel")
	public Result cancel(long id) {
		Order order = orderService.getById(id);
		Assert.notNull(order, "找不到订单");
		Assert.isTrue(order.getUserId() == StpKit.getUserId().longValue(), "无权限操作");

		orderService.autoCancel(id);
		return Result.success();
	}

	@PostMapping("/pay")
	public Result pay(long id) {
		Order order = orderService.getById(id);
		Assert.notNull(order, "找不到订单");
		Assert.isTrue(order.getUserId() == StpKit.getUserId().longValue(), "无权限操作");

		Assert.isTrue(order.getStatus() == OrderStatusConstants.PENDING_PAYMENT, "订单状态不对");

		order.setStatus(OrderStatusConstants.PAID_AWAITING_CHECKIN);
		orderService.updateById(order);

		return Result.success();
	}

	@GetMapping("/list")
	public Result list(Integer status) {

		Page<Order> pageData = orderService.page(getPage(), new QueryWrapper<Order>()
				.eq("user_id", StpKit.getUserId())
				.eq(status != null, "status", status)
				.orderByDesc("create_time")
		);

		return Result.success(pageData);
	}

	@GetMapping("/dict")
	public Result dict() {
		return Result.success(HotelDict.getMapList(HotelDict.statusDict));
	}
}
