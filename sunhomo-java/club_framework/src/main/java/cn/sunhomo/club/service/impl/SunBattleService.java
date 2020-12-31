package cn.sunhomo.club.service.impl;

import cn.sunhomo.club.domain.SunBattle;
import cn.sunhomo.club.mapper.SunBattleDao;
import cn.sunhomo.club.mapper.SunMemberDao;
import cn.sunhomo.club.service.ISunBattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 1、只能限定为活动报名人员
 * 2、约战的战绩只影响实时积分、年度积分，不影响总积分
 * 3、约战分单打、双打；
 * 4、退报，约战自动取消，实时恢复暂扣的积分
 * 5、约战可押积分，参战人员中最低分，最高5分，实时暂扣
 */
@Service
public class SunBattleService implements ISunBattleService {
    @Autowired
    private SunBattleDao battleDao;

    @Autowired
    private SunMemberDao memberDao;

    @Override
    @Transactional
    public int insertBattle(SunBattle battle) {
        //暂扣参战人员的个人积分
        Integer[] battlers = {battle.getA1(), battle.getA2(), battle.getB1(), battle.getB2()};
        memberDao.addPoint(battlers, -battle.getBattlePoint());
        return battleDao.insert(battle);
    }

    @Override
    @Transactional
    public int cancelBattle(SunBattle battle) {
        //恢复暂扣的个人积分
        Integer[] battlers = {battle.getA1(), battle.getA2(), battle.getB1(), battle.getB2()};
        memberDao.addPoint(battlers, battle.getBattlePoint());
        return battleDao.deleteByPrimaryKey(new Integer[]{battle.getBattleId()});
    }

    @Override
    public int setResult(Integer battleId, Byte battleResult) {
        SunBattle battle = new SunBattle();
        battle.setBattleId(battleId);
        battle.setBattleResult(battleResult);
        return battleDao.updateByPrimaryKeySelective(battle);
    }

    @Override
    public int updateBattle(SunBattle battle) {
        return battleDao.updateByPrimaryKey(battle);
    }

    @Override
    public int deleteByPrimaryKey(Integer[] ids) {
        return battleDao.deleteByPrimaryKey(ids);
    }

    @Override
    public SunBattle selectByPrimaryKey(Integer battleId) {
        return battleDao.selectByPrimaryKey(battleId);
    }

    @Override
    public List<SunBattle> selectBattlesByActivityId(Integer activityId) {
        return battleDao.selectBattlesByActivityId(activityId);
    }

    @Override
    public List<SunBattle> selectBattlesByMemberId(Integer memberId) {
        return battleDao.selectBattlesByMemberId(memberId);
    }
}
