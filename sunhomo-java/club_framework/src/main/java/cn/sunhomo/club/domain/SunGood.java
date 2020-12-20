package cn.sunhomo.club.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * SUN_GOOD
 * @author 
 */
@Data
public class SunGood extends BaseEntity {
    private Integer goodId;

    /**
     * 商品名称
     */
    private String goodName;

    /**
     * 商品图片
     */
    private String goodPhoto;

    /**
     * 兑换所需积分
     */
    private Integer value;

    /**
     * 库存量
     */
    private Integer stock;

    /**
     * 兑换次数，用于展现商品热度
     */
    private Integer exchange;

    private static final long serialVersionUID = 1L;
}