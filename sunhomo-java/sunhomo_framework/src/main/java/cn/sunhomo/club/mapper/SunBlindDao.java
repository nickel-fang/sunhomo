package cn.sunhomo.club.mapper;

import cn.sunhomo.club.domain.SunBlind;
import org.apache.ibatis.annotations.Param;

/**
* @author: Nickel Fang
* @date: 2021/3/8 15:34
*/
public interface SunBlindDao {
    int insert(SunBlind record);
    int deleteMemberByActivityId(@Param("activityId") Integer activityId, @Param("memberId") Integer memberId);
}