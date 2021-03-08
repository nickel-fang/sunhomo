package cn.sunhomo.club.domain;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
* @author: Nickel Fang
* @date: 2021/3/8 15:34
*/
@Data
@AllArgsConstructor
public class SunBlind extends BaseEntity {
    private Integer memberId;

    private Integer activityId;

    /**
    * 报名约战时间，按照报名时间取4X人
    */
    private Date enrollTime;

    private String memberName;
}