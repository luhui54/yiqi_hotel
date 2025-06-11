package com.yiqi.api_backend.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiqi.api_backend.common.lang.OrderStatusConstants;
import com.yiqi.api_backend.entity.Hotel;
import com.yiqi.api_backend.entity.Order;
import com.yiqi.api_backend.entity.RoomType;
import com.yiqi.api_backend.mapper.OrderMapper;
import com.yiqi.api_backend.security.StpKit;
import com.yiqi.api_backend.service.HotelService;
import com.yiqi.api_backend.service.OrderService;
import com.yiqi.api_backend.service.RoomTypeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author luHui 广东东软学院 24软升5班
 * @since 2024-11-13
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    HotelService hotelService;

    @Resource
    RoomTypeService typeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long create(Order order) {
        RoomType roomType = typeService.getById(order.getRoomTypeId());
        Assert.notNull(roomType, "房间类型不存在");

        Assert.isTrue(roomType.getQuantity() > 0, "该酒店房型已定满！");

        Hotel hotel = hotelService.getById(roomType.getHotelId());

        order.setUserId(StpKit.getUserId());
        order.setSn(createSn());
        order.setBookTime(LocalDateTime.now());
        order.setCreateTime(LocalDateTime.now());
        order.setPrice(roomType.getPrice());

        order.setHotelId(hotel.getId());
        order.setHotelTitle(hotel.getTitle());
        order.setRoomTypeName(roomType.getName());
        order.setRoomTypeImage(roomType.getImage());
        order.setStatus(OrderStatusConstants.PENDING_PAYMENT);

        this.save(order);

        // 扣减房型库存
        roomType.setQuantity(roomType.getQuantity() - 1);
        typeService.updateById(roomType);

        // Timer 设置定时任务，订单创建成功后10分钟内没有支付，就会自动取消订单，并回馈库存
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                autoCancel(order.getId());
            }
        }, 10 * 60 * 1000);

        return order.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void autoCancel(long id) {
        Order order = this.getById(id);
        if (order.getStatus() != OrderStatusConstants.PENDING_PAYMENT) {
            return;
        }

        order.setStatus(OrderStatusConstants.CANCELLED);
        this.updateById(order);

        RoomType roomType = typeService.getById(order.getRoomTypeId());
        // 增加房型库存
        roomType.setQuantity(roomType.getQuantity() + 1);
        typeService.updateById(roomType);
    }


    private String createSn() {
        return DateUtil.format(new Date(), "yyMMddHHmms" + RandomUtil.randomNumbers(3));
    }

}
