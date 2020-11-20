package cn.sunhomo.club.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * SUN_POINT_RECORD
 *
 * @author
 */
@Data
@AllArgsConstructor
public class SunPointRecord implements Serializable {
    private Integer pointRecordId;

    /**
     * 会员ID
     */
    private Integer memberId;

    /**
     * 积分交易类型 1：参加打球活动 2：参加比赛活动 3：约战 4：兑换商品 5:系统奖励积分
     */
    private Byte pointRecordType;

    /**
     * 积分交易说明
     */
    private String pointRecordMemo;

    /**
     * 积明交易额，获得积分为正数，消费积分为数
     */
    private Integer pointRecordValue;

    /**
     * 交易日期
     */
    private Date pointRecordDate;

    private static final long serialVersionUID = 1L;
}