package cn.sunhomo.club.mapper;

import cn.sunhomo.club.domain.SunBattle;

import java.util.List;

public interface SunBattleDao {
    int deleteByPrimaryKey(Integer[] battleId);

    int insert(SunBattle record);

    int insertSelective(SunBattle record);

    SunBattle selectByPrimaryKey(Integer battleId);

    int updateByPrimaryKeySelective(SunBattle record);

    int updateByPrimaryKey(SunBattle record);

    List<SunBattle> selectBattlesByActivityId(Integer activityId);

    List<SunBattle> selectBattlesFromNow(String now);

    int getBattleState(Integer battleId);

    boolean hasNotCompletedBattlesByMemberId(Integer memberId);
}