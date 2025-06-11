package com.yiqi.api_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author luHui 广东东软学院 24软升5班
 * @since 2024-11-13
 */
@Getter
@Setter
  @TableName("app_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 订单编号
     */
      private String sn;

    private Long userId;

    private Long hotelId;

    private Long roomTypeId;

    private Long roomId;

    private String roomSn;

      /**
     * 入住人名字
     */
      private String guestName;

      /**
     * 入住人手机号
     */
      private String guestPhone;

      /**
     * 预订时间
     */
      private LocalDateTime bookTime;

      /**
     * 预订入住时间
     */
      private LocalDate bookCheckInTime;

      /**
     * 预订退房时间
     */
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
     * 订单状态
     */
      private Integer status;

    private LocalDateTime createTime;
}
