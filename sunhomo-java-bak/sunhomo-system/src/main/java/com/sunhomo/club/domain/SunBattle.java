package com.sunhomo.club.domain;

import java.util.Date;

import com.sunhomo.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * SUN_BATTLE
 *
 * @author
 */
@Data
public class SunBattle extends BaseEntity {
    private Integer battleId;

    /**
     * 所属活动, ACTIVITY.ACTIVITY_ID
     */
    private Integer activityId;

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
    private Date battleDate;

    /**
     * 约战的押注，多少个积分
     */
    private Integer battlePoint;

    /**
     * 约战的状态， -1 取消； 1111分别表示4位选手的应战确认，A1肯定为1，因为A1为发起者
     */
    private Integer battleState;

    /**
     * 约战结束，1为A胜，-1为A负
     */
    private Byte battleResult;

    private static final long serialVersionUID = 1L;
}