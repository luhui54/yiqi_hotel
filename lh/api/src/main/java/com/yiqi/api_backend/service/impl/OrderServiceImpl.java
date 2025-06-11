package com.yiqi.api_backend.service.impl;

import com.yiqi.api_backend.entity.Order;
import com.yiqi.api_backend.mapper.OrderMapper;
import com.yiqi.api_backend.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author luHui 广东东软学院 24软升5班
 * @since 2024-11-13
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
