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

    //查询某会员是否有待确认的约战活动
    public boolean hasNotCompletedBattlesByMemberId(Integer memberId);

    List<SunBattle> selectBattlesFromNow();

    int confirmAndCancelAndWin(SunBattle battle);

    int doCall(SunBattleVote vote);

    int getBattleState(Integer battleId);

    int accept(SunBattle battle, Integer accepter);
}
