package cn.sunhomo.club.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * SUN_GOOD_TRANSACTION
 *
 * @author
 */
@Data
@AllArgsConstructor
public class SunGoodTransaction extends BaseEntity {
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date goodTransactionDate;

    /**
     * 兑换状态， 1：未交付， 2：已交付
     */
    private Byte state;

    private String goodName;

    private static final long serialVersionUID = 1L;
}