package com.yiqi.api_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yiqi.api_backend.common.dictionary.HotelDict;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author luHui 广东东软学院 24软升5班
 * @since 2024-11-13
 */
@Data
@TableName("app_hotel")
public class Hotel implements Serializable {


    @Serial
    private static final long serialVersionUID = 4376278089309144060L;

    /**
     * 酒店ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 酒店名称
     */
    @TableField("title")
    private String title;

    /**
     * 品牌
     */
    @TableField("brand")
    private String brand;

    /**
     * 封面
     */
    @TableField("image")
    private String image;

    /**
     * 所在城市
     */
    @TableField("city")
    private String city;

    /**
     * 图集
     */
    @TableField("images")
    private String images;

    /**
     * 电话
     */
    @TableField("tel")
    private String tel;

    /**
     * 所在地址
     */
    @TableField("location")
    private String location;

    /**
     * 简介
     */
    @TableField("introduction")
    private String introduction;

    /**
     * 评分
     */
    @TableField("score")
    private Double score;

    /**
     * 等级
     */
    @TableField("hotel_rank")
    private Integer hotelRank;

    /**
     * 最低价
     */
    @TableField("min_price")
    private BigDecimal minPrice;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField(exist = false)
    private String rankStr;

    public String getRankStr() {
        return HotelDict.rankDict.get(hotelRank);
    }
}
