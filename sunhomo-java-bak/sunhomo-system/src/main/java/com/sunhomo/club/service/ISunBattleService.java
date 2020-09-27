package com.sunhomo.club.service;

import com.sunhomo.club.domain.SunBattle;

import java.util.List;

public interface ISunBattleService {
    public int insertBattle(SunBattle battle);

    public int updateBattle(SunBattle battle);

    public int deleteByPrimaryKey(Integer[] ids);

    public List<SunBattle> selectBattlesByActivityId(Integer activityId);

    //查询某会员参加过的约战活动
    public List<SunBattle> selectBattlesByMemberId(Integer memberId);
}
