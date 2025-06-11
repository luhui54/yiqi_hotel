package com.yiqi.api_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
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
  @TableName("app_hotel")
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 酒店名称
     */
      private String title;

      /**
     * 品牌
     */
      private String brand;

      /**
     * 封面
     */
      private String image;

      /**
     * 所在城市
     */
      private String city;

      /**
     * 图集
     */
      private String images;

      /**
     * 电话
     */
      private String tel;

      /**
     * 所在地址
     */
      private String location;

      /**
     * 简介
     */
      private String introduction;

      /**
     * 评分
     */
      private Double score;

      /**
     * 等级
     */
      private Integer rank;

      /**
     * 最低价
     */
      private BigDecimal minPrice;

      /**
     * 创建时间
     */
      private LocalDateTime createTime;
}
