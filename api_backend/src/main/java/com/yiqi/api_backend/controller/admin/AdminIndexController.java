package com.yiqi.api_backend.controller.admin;


import cn.hutool.core.map.MapUtil;
import com.yiqi.api_backend.common.base.BaseController;
import com.yiqi.api_backend.common.lang.Result;
import com.yiqi.api_backend.mapper.OrderMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminIndexController extends BaseController {

	@Resource
	OrderMapper orderMapper;

	@GetMapping("/data")
	public Result data() {
		Map<String, Object> todayMap = orderMapper.getTodayCountAndAmount();
		Map<String, Object> monthMap = orderMapper.getMonthCountAndAmount();

		return Result.success(MapUtil.builder().put("todayMap", todayMap).put("monthMap", monthMap).build());
	}

}
