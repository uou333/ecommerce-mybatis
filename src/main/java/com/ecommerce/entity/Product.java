package com.ecommerce.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体类，对应数据库表 t_product
 *
 * @author 实验小组
 */
@Data
public class Product {

    /** 主键，自增 */
    private Long id;

    /** 商品名称 */
    private String name;

    /** 价格 */
    private BigDecimal price;

    /** 库存 */
    private Integer stock;

    /** 分类 ID */
    private Long categoryId;

    /** 图片地址 */
    private String imageUrl;

    /** 商品描述 */
    private String description;

    /** 状态：1 上架 / 0 下架 */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;
}
