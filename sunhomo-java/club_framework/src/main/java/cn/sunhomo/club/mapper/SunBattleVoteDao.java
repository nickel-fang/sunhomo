package cn.sunhomo.club.mapper;

import cn.sunhomo.club.domain.SunBattleVote;

import java.util.List;

public interface SunBattleVoteDao {
    int insert(SunBattleVote record);

    int insertSelective(SunBattleVote record);

    List<SunBattleVote> selectVoteByBattleId(Integer battleId);
}