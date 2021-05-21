package cn.sunhomo.club.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: Nickel Fang
 * @date: 2021/2/1 10:38
 */
@Data
public class SunFinance extends BaseEntity {
    private Integer financeId;

    /**
     * 交易日期
     */
    private String finDate;

    /**
     * 交易类型 1：收入 2：支出 3：其他
     */
    private Byte finType;

    /**
     * 交易说明
     */
    private String finMemo;

    /**
     * 交易金额
     */
    private Integer finValue;

    /**
     * 交易总额
     */
    private Integer finSum;
}