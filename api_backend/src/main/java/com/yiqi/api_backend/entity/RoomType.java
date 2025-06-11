package com.yiqi.api_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author luHui 广东东软学院 24软升5班
 * @since 2024-11-13
 */
@Getter
@Setter
@TableName("app_room_type")
public class RoomType implements Serializable {


    @Serial
    private static final long serialVersionUID = -3311855155856512053L;

    /**
     * 房间类型id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属酒店
     */
    private Long hotelId;

    @TableField(exist = false)
    private String hotelTitle;

    /**
     * 房间名称
     */
    private String name;

    /**
     * 图片
     */
    private String image;

    /**
     * 图集
     */
    private String images;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 面积
     */
    private String area;

    /**
     * 标签
     */
    private String tags;

    /**
     * 床位
     */
    private String bed;

    /**
     * 说明
     */
    private String instruction;

    /**
     * 剩余数量
     */
    private Integer quantity;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
