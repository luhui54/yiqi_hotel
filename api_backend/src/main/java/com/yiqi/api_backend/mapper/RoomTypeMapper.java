package com.yiqi.api_backend.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiqi.api_backend.entity.RoomType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author luHui 广东东软学院 24软升5班
 * @since 2024-11-13
 */
@Mapper
public interface RoomTypeMapper extends BaseMapper<RoomType> {

    @Select("select yiQi.*, h.title as hotelTitle from app_room_type yiQi left join app_hotel h " +
            "on yiQi.hotel_id = h.id  ${ew.customSqlSegment}")
    Page<RoomType> getPage(Page<Object> page,@Param(Constants.WRAPPER) QueryWrapper<RoomType> wrapper);
}
