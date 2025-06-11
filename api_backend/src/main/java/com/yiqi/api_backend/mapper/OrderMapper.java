package com.yiqi.api_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiqi.api_backend.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * @author luHui 广东东软学院 24软升5班
 * @since 2024-11-13
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Select("SELECT count(*) AS count, ifnull(sum(price), 0) AS total FROM app_order WHERE STATUS IN (3, 4)" +
            " AND DATE(real_check_in_time) = CURDATE()")
    Map<String, Object> getTodayCountAndAmount();

    @Select("SELECT count(*) AS count, ifnull(sum(price), 0) AS total FROM app_order WHERE STATUS IN (3, 4) " +
            "AND DATE_FORMAT(real_check_in_time, '%Y-%m') = DATE_FORMAT(CURDATE(), '%Y-%m')")
    Map<String, Object> getMonthCountAndAmount();
}
