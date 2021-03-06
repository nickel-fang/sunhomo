package com.sunhomo.club.service.impl;

import com.sunhomo.club.domain.SunBattle;
import com.sunhomo.club.mapper.SunBattleDao;
import com.sunhomo.club.service.ISunBattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SunBattleService implements ISunBattleService {
    @Autowired
    private SunBattleDao battleDao;

    @Override
    public int insertBattle(SunBattle battle) {
        return battleDao.insert(battle);
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
    public List<SunBattle> selectBattlesByActivityId(Integer activityId) {
        return battleDao.selectBattlesByActivityId(activityId);
    }

    @Override
    public List<SunBattle> selectBattlesByMemberId(Integer memberId) {
        return battleDao.selectBattlesByMemberId(memberId);
    }

}
