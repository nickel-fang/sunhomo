package cn.sunhomo.club.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * SUN_GOOD_TRANSACTION
 * @author 
 */
@Data
@AllArgsConstructor
public class SunGoodTransaction implements Serializable {
    private Integer goodTransactionId;

    /**
     * 商品ID，GOOD.GOOD_ID
     */
    private Integer goodId;

    /**
     * 商品兑换人， MEMBER.MEMBER_ID
     */
    private Integer memberId;

    /**
     * 商品兑换日期
     */
    private Date goodTransactionDate;

    private static final long serialVersionUID = 1L;
}