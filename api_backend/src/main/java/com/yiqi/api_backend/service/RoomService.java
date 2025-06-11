package com.yiqi.api_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiqi.api_backend.entity.Room;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author luHui 广东东软学院 24软升5班
 * @since 2024-11-13
 */
public interface RoomService extends IService<Room> {

    Page<Room> getPage(Page<Object> page, QueryWrapper<Room> wrapper);
}
