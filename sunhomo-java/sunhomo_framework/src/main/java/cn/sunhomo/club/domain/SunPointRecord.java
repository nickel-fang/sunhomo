package cn.sunhomo.club.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
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
     * 积分交易类型（>100只影响实时积分） 1：参加打球活动 2：参加比赛活动 3：约战  5:系统奖励积分 101：兑换商品 102：系统奖励实时积分 103：约战押注
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date pointRecordDate;

    private static final long serialVersionUID = 1L;
}