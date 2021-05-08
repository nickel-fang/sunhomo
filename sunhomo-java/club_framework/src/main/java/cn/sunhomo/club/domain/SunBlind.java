package cn.sunhomo.club.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Nickel Fang
 * @date: 2021/3/8 15:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SunBlind extends BaseEntity {
    private Integer memberId;

    private Integer activityId;

    /**
     * 报名约战时间，按照报名时间取4X人
     * 改为redis方式后，list自带顺序，此属性暂时无用
     */
    private Date enrollTime;

    private String memberName;
}