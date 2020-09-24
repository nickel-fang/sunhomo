package com.sunhomo.club.domain;

import java.util.List;

import com.sunhomo.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * SUN_DIVISION
 *
 * @author
 */
@Data
public class SunDivision extends BaseEntity {
    private Integer divisionId;

    /**
     * 所属活动 ACTIVITY.ACTIVITY_ID
     */
    private Integer activityId;

    /**
     * 分组名
     */
    private String divisionName;

    /**
     * 队长， MEMBER.MEMBER_ID
     */
    private Integer leader;
    private String leaderName;

    /**
     * 名次， 1为冠军， 2为亚军，3为季军，后面直接显示参与
     */
    private Byte rank;

    private List<SunMember> members;

    private static final long serialVersionUID = 1L;
}