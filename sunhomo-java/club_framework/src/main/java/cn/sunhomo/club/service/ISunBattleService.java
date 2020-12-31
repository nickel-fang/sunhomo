package cn.sunhomo.club.service;

import cn.sunhomo.club.domain.SunBattle;

import java.util.List;

public interface ISunBattleService {
    public int insertBattle(SunBattle battle);

    public int updateBattle(SunBattle battle);

    public int deleteByPrimaryKey(Integer[] ids);

    public SunBattle selectByPrimaryKey(Integer battleId);

    public List<SunBattle> selectBattlesByActivityId(Integer activityId);

    //查询某会员参加过的约战活动
    public List<SunBattle> selectBattlesByMemberId(Integer memberId);

    int cancelBattle(SunBattle battle);

    int setResult(Integer battleId, Byte state);
}
