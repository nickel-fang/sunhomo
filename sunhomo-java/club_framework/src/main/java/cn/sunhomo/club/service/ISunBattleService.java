package cn.sunhomo.club.service;

import cn.sunhomo.club.domain.SunBattle;
import cn.sunhomo.club.domain.SunBattleVote;

import java.util.List;

public interface ISunBattleService {
    public int insertBattle(SunBattle battle);

    public int updateBattle(SunBattle battle);

    public int deleteByPrimaryKey(Integer[] ids);

    public SunBattle selectByPrimaryKey(Integer battleId);

    public List<SunBattle> selectBattlesByActivityId(Integer activityId);

    List<SunBattle> selectBattlesByActivityIdAndMemberId(Integer activityId, Integer memberId);

    //查询某会员是否有待确认的约战活动
    public boolean hasNotCompletedBattlesByMemberId(Integer memberId);

    List<SunBattle> selectBattlesFromNow();

    int confirmAndCancelAndWin(SunBattle battle);

    int doCall(SunBattleVote vote);

    int getBattleState(Integer battleId);

    int accept(SunBattle battle, Integer accepter);

    int quit(Integer battleId, String position, Integer quiter, Integer battlePoint);

    List<SunBattle> selectNotFormedBlindBattles(String date);

    boolean hasNotCompletedBlindBattles(String battleDate);
}
