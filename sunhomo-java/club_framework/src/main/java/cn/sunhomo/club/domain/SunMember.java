package cn.sunhomo.club.domain;

import java.util.Date;

import lombok.Data;

/**
 * SUN_MEMBER
 *
 * @author
 */
@Data
public class SunMember extends BaseEntity{
    private Integer memberId;

    /**
     * 用户微信小程序唯一身份
     */
    private String openid;

    /**
     * 用户名，默认微信昵称
     */
    private String memberName;

    /**
     * 用户图像，直接取微信图片
     */
    private String memberPhoto;

    /**
     * 总积分，累积参加活动及比赛的积分
     */
    private Integer totalPoint;

    /**
     * 当年总积分，累积当年参加活动及比赛的积分
     */
    private Integer yearPoint;

    /**
     * 实时积分，参加活动及比赛、约战、消费的积分
     */
    private Integer point;

    /**
     * 约战胜局
     */
    private Integer winNumber;

    /**
     * 约战负局
     */
    private Integer loseNumber;

    /**
     * 胜率 = WIN_NUMBER/(WIN_NUMBER+LOSE_NUMBER)，取4位数，如98.33%，数据存中9833
     */
    private Short ratio;

    /**
     * 注册日期
     */
    private Date signDate;

    /**
     * 报名时所用，主报人、挂
     */
    private Byte isMaster;

    /**
     *  是否为管理者
     */
    private Byte isAdmin;

    private static final long serialVersionUID = 1L;
}