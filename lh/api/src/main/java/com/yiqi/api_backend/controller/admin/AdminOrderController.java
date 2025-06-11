package com.markerhub.controller.admin;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.common.base.BaseController;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Order;
import com.markerhub.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/order")
public class AdminOrderController extends BaseController {

	@Resource
    OrderService orderService;

	@GetMapping("/page")
	public Result page(Order order) {
		Page<Order> pageData = orderService.page(getPage(), queryLike(order));
		return Result.success(pageData);
	}

	@GetMapping("/info/{id}")
	public Result info(@PathVariable long id) {
        Order order =orderService.getById(id);
		return Result.success(order);
	}

	@PostMapping("/save")
	public Result save(@Validated @RequestBody Order order) {
		if (order.getId() == null) {
            order.setCreateTime(LocalDateTime.now());
		}
		orderService.saveOrUpdate(order);
		return Result.success();
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody Long[] ids) {
		Assert.isTrue(ArrayUtil.isNotEmpty(ids), "请选择删除选项");
		orderService.removeBatchByIds(List.of(ids));
		return Result.success();
	}
}
