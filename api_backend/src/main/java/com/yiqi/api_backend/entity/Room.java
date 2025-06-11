package com.yiqi.api_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author luHui 广东东软学院 24软升5班
 * @since 2024-11-13
 */
@Getter
@Setter
@TableName("app_room")
public class Room implements Serializable {


    @Serial
    private static final long serialVersionUID = -6696424603924974324L;

    /**
     * 房间ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属房型
     */
    private Long roomTypeId;

    /**
     * 所属酒店
     */
    private Long hotelId;

    /**
     * 房间号
     */
    private String sn;

    /**
     * 房间状态
     */
    private Integer status;

    private LocalDateTime createTime;

    @TableField(exist = false)
    private String roomTypeName;

    @TableField(exist = false)
    private String hotelTitle;
}
