package cn.sunhomo.club.domain;


import lombok.Data;

import java.util.List;

/**
 * SUN_ACTIVITY
 *
 * @author
 */
@Data
public class SunActivity{
    private Integer activityId;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动类型， 1：普通打球活动 2：比赛活动
     */
    private Byte activityType;

    /**
     * 活动日期
     */
    private String activityDate;

    /**
     * 活动开始时间
     */
    private String startTime;

    /**
     * 活动结束时间
     */
    private String endTime;

    /**
     * 活动报名、抽签截止时间
     */
    private String deadline;

    /**
     * 活动地点
     */
    private String place;

    /**
     * 比赛场地，如7:8:9
     */
    private String field;

    /**
     * 活动规则
     */
    private String activityRule;

    /**
     * 活动人数
     */
    private Integer numbers;

    /**
     * 备注
     */
    private String memo;

    /**
     * 活动状态， 1：报名中， 2：活动中， 3：已结束， 4：已取消
     */
    private Byte activityState;

    //活动（当为activityType=2比赛活动）下的分组
    private List<SunDivision> divisions;
    //活动下的报名会员
    private List<SunMember> members;

    private static final long serialVersionUID = 1L;
}