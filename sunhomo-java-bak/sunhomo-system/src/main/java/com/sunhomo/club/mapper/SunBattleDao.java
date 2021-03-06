package com.sunhomo.club.mapper;

import com.sunhomo.club.domain.SunBattle;

import java.util.List;

public interface SunBattleDao {
    int deleteByPrimaryKey(Integer[] battleId);

    int insert(SunBattle record);

    int insertSelective(SunBattle record);

    SunBattle selectByPrimaryKey(Integer battleId);

    int updateByPrimaryKeySelective(SunBattle record);

    int updateByPrimaryKey(SunBattle record);

    List<SunBattle> selectBattlesByActivityId(Integer activityId);

    List<SunBattle> selectBattlesByMemberId(Integer memberId);
}