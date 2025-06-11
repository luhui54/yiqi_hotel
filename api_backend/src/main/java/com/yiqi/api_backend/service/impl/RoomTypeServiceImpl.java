package com.yiqi.api_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiqi.api_backend.entity.RoomType;
import com.yiqi.api_backend.mapper.RoomTypeMapper;
import com.yiqi.api_backend.service.RoomTypeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author luHui 广东东软学院 24软升5班
 * @since 2024-11-13
 */
@Service
public class RoomTypeServiceImpl extends ServiceImpl<RoomTypeMapper, RoomType> implements RoomTypeService {

    @Resource
    RoomTypeMapper typeMapper;

    @Override
    public Page<RoomType> getPage(Page<Object> page, QueryWrapper<RoomType> wrapper) {
        return typeMapper.getPage(page, wrapper);
    }
}
