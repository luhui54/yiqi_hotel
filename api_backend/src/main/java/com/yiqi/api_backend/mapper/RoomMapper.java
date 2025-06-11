package com.yiqi.api_backend.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiqi.api_backend.entity.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author luHui 广东东软学院 24软升5班
 * @since 2024-11-13
 */
@Mapper
public interface RoomMapper extends BaseMapper<Room> {

    /**
     * 分页查询
     * @param page 分页对象
     * @param wrapper 查询条件
     * @return 分页结果
     */
    @Select("select yiQi.*, rt.name as roomTypeName, h.title as hotelTitle " +
            "from app_room yiQi  " +
            "left join app_room_type rt on yiQi.room_type_id = rt.id " +
            "left join app_hotel h on yiQi.hotel_id = h.id  " +
            "${ew.customSqlSegment}")
    Page<Room> getPage(Page<Object> page,@Param(Constants.WRAPPER) QueryWrapper<Room> wrapper);
}
