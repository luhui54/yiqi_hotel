package com.yiqi.api_backend.controller.app;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiqi.api_backend.common.base.BaseController;
import com.yiqi.api_backend.common.dictionary.HotelDict;
import com.yiqi.api_backend.common.dto.HotelDto;
import com.yiqi.api_backend.common.lang.Result;
import com.yiqi.api_backend.entity.Hotel;
import com.yiqi.api_backend.entity.RoomType;
import com.yiqi.api_backend.service.HotelService;
import com.yiqi.api_backend.service.RoomTypeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/hotel")
public class HotelController extends BaseController {

	@Resource
	private HotelService hotelService;

	@Resource
	private RoomTypeService typeService;

	@GetMapping("/dict")
	public Result dict() {

		return Result.success(MapUtil.builder()
				.put("scoreDict", HotelDict.getMapList(HotelDict.scoreDict))
				.put("rankDict", HotelDict.getMapList(HotelDict.rankDict))
				.put("brandDict", HotelDict.getMapList(HotelDict.brandDict))
				.put("priceDict", HotelDict.getMapList(HotelDict.priceDict))
				.build());
	}

	@GetMapping("/search")
	public Result search(HotelDto hotel) {

		Assert.notBlank(hotel.getDestination(), "请输入目的地!");

		Page<Hotel> pageData = hotelService.page(getPage(), new QueryWrapper<Hotel>()
				.like("city", hotel.getDestination())
				.eq(hotel.getBrand() != null, "brand", HotelDict.brandDict.get(hotel.getBrand()))
				.eq(hotel.getRank() != null, "rank", hotel.getRank())

				.ge(hotel.getScore() != null && hotel.getScore() == 1, "score", 4.8)
				.ge(hotel.getScore() != null && hotel.getScore() == 2, "score", 4.5)
				.ge(hotel.getScore() != null && hotel.getScore() == 3, "score", 4.0)


				.lt(hotel.getPrice() != null && hotel.getPrice() == 1, "min_price", "150") // 小于150
				.between(hotel.getPrice() != null && hotel.getPrice() == 2, "min_price", "150", "300") // 150-300
				.between(hotel.getPrice() != null && hotel.getPrice() == 3, "min_price", "301", "450") // 150-300
				.between(hotel.getPrice() != null && hotel.getPrice() == 4, "min_price", "451", "700") // 150-300
				.gt(hotel.getPrice() != null && hotel.getPrice() == 5, "min_price", "700") // 大于700

				.and(StrUtil.isNotBlank(hotel.getKeyword()), i ->
						i.and(j -> j.like("brand", hotel.getKeyword()))
								.or(j -> j.like("location", hotel.getKeyword()))
								.or(j -> j.like("title", hotel.getKeyword()))
				)
		);
		return Result.success(pageData);
	}

	@GetMapping("/detail")
	public Result detail(long id) {
		Hotel hotel = hotelService.getById(id);
		List<RoomType> roomTypes = typeService.list(new QueryWrapper<RoomType>().eq("hotel_id", id));
		return Result.success(MapUtil.builder()
				.put("hotel", hotel)
				.put("roomTypes", roomTypes)
				.build()
		);
	}

}
