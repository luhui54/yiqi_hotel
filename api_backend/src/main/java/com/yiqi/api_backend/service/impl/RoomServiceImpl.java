package com.yiqi.api_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiqi.api_backend.entity.Room;
import com.yiqi.api_backend.mapper.RoomMapper;
import com.yiqi.api_backend.service.RoomService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author luHui 广东东软学院 24软升5班
 * @since 2024-11-13
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

    @Resource
    RoomMapper roomMapper;

    @Override
    public Page<Room> getPage(Page<Object> page, QueryWrapper<Room> wrapper) {
        return roomMapper.getPage(page, wrapper);
    }
}
