package cn.sunhomo.club.domain;


import cn.sunhomo.util.StringUtils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * SUN_ACTIVITY
 *
 * @author
 */
@Data
public class SunActivity extends BaseEntity {
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
     * 退报截止时间，一般为活动开始前24小时
     */
    private String deadline;

    /**
     * 开始抽签时间
     */
    private String drawTime;

    /**
     * 活动地点
     */
    private String place;

    /**
     * 比赛场地，如7:8:9
     */
    private String field;

    /**
     * 活动人数
     */
    private Integer numbers;

    /**
     * 比赛活动分组数
     */
    private Integer divisions;

    /**
     * 报名费
     */
    private Integer fee;

    /**
     * 备注
     */
    private String memo;

    /**
     * 活动状态， 1：报名中， 2：活动中， 3：已结束， 4：已取消
     */
    private Byte activityState;

    //活动（当为activityType=2比赛活动）下的分组
//    private List<SunDivision> divisions;
    //活动下的报名会员
    private List<SunMember> members;

    /**
     * 前端定制，复制报名贴时，直接获取此字段
     */
    private String content;

    public String getContent() {
        if (members == null) return null;
        StringBuilder sb = new StringBuilder();
        sb.append("@所有人：请使用本群微信小程序进行活动报名\n")
                .append(activityName).append("\n")
                .append("1.活动时间：").append(activityDate).append("（").append(startTime).append(" - ").append(endTime).append("）").append("\n")
                .append("2.活动地点：").append(place).append("（").append(field).append("）\n")
                .append("3.活动费用：").append(fee).append("元/人").append("\n\n");
        SunMember member;
        for (int i = 0; i < members.size() && i < numbers; i++) {
            member = members.get(i);
            sb.append(i + 1).append(". ").append(member.getMemberName());
            if (member.getIsMaster() == 0) {
                //主报人
                sb.append("（积分:").append(member.getYearPoint()).append("/").append(member.getPoint()).append("）\n");
            } else {
                sb.append(" +" + member.getIsMaster()).append("\n");
            }

        }
        //有替补
        if (members.size() > numbers) {
            sb.append("替补：\n");
            for (int i = numbers; i < members.size(); i++) {
                member = members.get(i);
                sb.append(i - numbers + 1).append(". ").append(member.getMemberName());
                if (member.getIsMaster() == 0) {
                    //主报人
                    sb.append("(积分:").append(member.getYearPoint()).append("/").append(member.getPoint()).append(")\n");
                } else {
                    sb.append(" +" + member.getIsMaster()).append("\n");
                }
            }
        }

        if (StringUtils.isNotEmpty(memo)) sb.append("\n").append(memo);
        return sb.toString();
    }

    private boolean canDraw;

    private static final long serialVersionUID = 1L;
}