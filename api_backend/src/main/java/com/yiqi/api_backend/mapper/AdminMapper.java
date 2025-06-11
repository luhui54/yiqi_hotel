package com.yiqi.api_backend.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiqi.api_backend.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    @Select("select a.*, h.title as hotelTitle from sys_admin a left join app_hotel h on a.hotel_id = h.id ${ew.customSqlSegment}")
    Page<Admin> getPage(Page<Object> page,@Param(Constants.WRAPPER) QueryWrapper<Admin> adminQueryWrapper);
}




