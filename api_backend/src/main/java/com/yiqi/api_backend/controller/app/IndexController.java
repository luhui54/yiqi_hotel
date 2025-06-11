package com.yiqi.api_backend.controller.app;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiqi.api_backend.common.dictionary.HotelDict;
import com.yiqi.api_backend.common.lang.Result;
import com.yiqi.api_backend.entity.Hotel;
import com.yiqi.api_backend.service.HotelService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName IndexController
 * @Description 首页请求接口
 * @Author lh
 * @Date 2024/11/13 15:03
 * @Version 1.0
 */
@RestController
@RequestMapping("/app")
public class IndexController {

    @Resource
    private HotelService hotelService;


    @RequestMapping("/index")
    public Result index(){
        List<Hotel> hotelList = hotelService.list(new QueryWrapper<Hotel>().orderByDesc("score").last("limit 0, 4"));

        // 使用 Stream API 格式化评分，保留小数点后一位
//        List<Hotel> formattedHotelList = hotelList.stream()
//                .peek(hotel -> {
//                    BigDecimal score = BigDecimal.valueOf(hotel.getScore()).setScale(1, RoundingMode.HALF_UP);
//                    hotel.setScore(score.doubleValue());
//                })
//                .collect(Collectors.toList());
        // 获取酒店星级基础数据
        List<Map<Object, Object>> rankDictMapList = HotelDict.getMapList(HotelDict.rankDict);
        return Result.success(MapUtil.builder().put("hotelList",hotelList).put("ranks",rankDictMapList).build());
    }
}
