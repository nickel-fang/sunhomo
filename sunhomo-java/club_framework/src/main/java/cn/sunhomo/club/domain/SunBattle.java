package cn.sunhomo.club.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Nickel Fang
 * @date: 2020/12/30 13:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SunBattle extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Integer battleId;

    /**
     * 所属活动, ACTIVITY.ACTIVITY_ID
     */
    private Integer activityId;

    /**
     * 约战类型，1：单打， 2：双打
     */
    private Byte battleType;

    /**
     * 选手A1，MEMBER.MEMBER_ID
     */
    private Integer a1;

    /**
     * 选手A2，MEMBER.MEMBER_ID
     */
    private Integer a2;

    /**
     * 选手B1，MEMBER.MEMBER_ID
     */
    private Integer b1;

    /**
     * 选手B2，MEMBER.MEMBER_ID
     */
    private Integer b2;

    private String a1Name;

    private String a2Name;

    private String b1Name;

    private String b2Name;

    /**
     * 约战的日期，冗余字段，可取ACTIVITY.ACTIVITY_DATE
     */
    private String battleDate;

    /**
     * 约战的押注，多少个积分
     */
    private Integer battlePoint;

    /**
     * 约战的状态， -1 取消；1 待确认 2 约战成功
     */
    private Integer battleState;

    /**
     * 约战结束，1为A胜，-1为A负
     */
    private Byte battleResult;

    /**
     * 是否为巅峰对决 1：是， -1 否
     */
    private Byte isPeak;
}