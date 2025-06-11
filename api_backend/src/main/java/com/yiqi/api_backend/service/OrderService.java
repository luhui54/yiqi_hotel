package com.yiqi.api_backend.service;

import com.yiqi.api_backend.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author luHui 广东东软学院 24软升5班
 * @since 2024-11-13
 */
public interface OrderService extends IService<Order> {

    @Transactional(rollbackFor = Exception.class)
    long create(Order order);

    @Transactional(rollbackFor = Exception.class)
    void autoCancel(long id);
}
