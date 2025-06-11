package com.yiqi.api_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yiqi.api_backend.common.dictionary.HotelDict;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author luHui 广东东软学院 24软升5班
 * @since 2024-11-13
 */
@Getter
@Setter
@TableName("app_order")
public class Order implements Serializable {


    @Serial
    private static final long serialVersionUID = -4325721117909739882L;

    /**
     * 订单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    private String sn;

    private Long userId;

    private Long hotelId;

    @NotNull(message = "请选择房间类型！")
    private Long roomTypeId;

    private Long roomId;

    private String roomSn;

    /**
     * 入住人名字
     */
    @NotBlank(message = "请填写入住人姓名！")
    private String guestName;

    /**
     * 入住人手机号
     */
    @NotBlank(message = "请填写入住人手机号！")
    private String guestPhone;

    /**
     * 预订时间
     */
    private LocalDateTime bookTime;

    /**
     * 预订入住时间
     */
    @NotNull(message = "参数异常！")
    private LocalDate bookCheckInTime;

    /**
     * 预订退房时间
     */
    @NotNull(message = "参数异常！")
    private LocalDate bookCheckOutTime;

    /**
     * 实际入住时间
     */
    private LocalDateTime realCheckInTime;

    /**
     * 实际退房时间
     */
    private LocalDateTime realCheckOutTime;

    private String hotelTitle;

    private String roomTypeName;

    private String roomTypeImage;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 订单状态 1:待支付 2:已支付待入住 3:已入住 4:已完成 5:已取消 6:已退款
     */
    private Integer status;

    private LocalDateTime createTime;

    @TableField(exist = false)
    private String statusStr;

    public String getStatusStr() {
        return HotelDict.statusDict.get(status);
    }
}
