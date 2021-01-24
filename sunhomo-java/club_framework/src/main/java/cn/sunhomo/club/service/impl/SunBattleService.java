package cn.sunhomo.club.service.impl;

import cn.sunhomo.club.domain.SunBattle;
import cn.sunhomo.club.domain.SunBattleVote;
import cn.sunhomo.club.mapper.SunBattleDao;
import cn.sunhomo.club.mapper.SunBattleVoteDao;
import cn.sunhomo.club.mapper.SunMemberDao;
import cn.sunhomo.club.service.ISunBattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private SunBattleVoteDao voteDao;

    @Override
    @Transactional
    public int insertBattle(SunBattle battle) {
        //暂扣参战人员的个人实时积分
//        int[] battlers = {battle.getA1(), battle.getA2(), battle.getB1(), battle.getB2()};
//        memberDao.addRealPoint(battlers, -battle.getBattlePoint());
        List<Integer> battlers = new ArrayList<>();
        battlers.add(battle.getA1());
        if (null != battle.getA2()) battlers.add(battle.getA2());
        if (null != battle.getB1()) battlers.add(battle.getB1());
        if (null != battle.getB2()) battlers.add(battle.getB2());
        memberDao.addRealPoint(battlers, -battle.getBattlePoint());
        return battleDao.insert(battle);
    }

    @Override
    @Transactional
    public int confirmAndCancelAndWin(SunBattle battle) {
        //取消要退回个人实时积分，退回押注者的实时积分
        if (battle.getBattleState() != null && battle.getBattleState() == -1) {
            SunBattle oldBattle = battleDao.selectByPrimaryKey(battle.getBattleId());
//            int[] battlers = {oldBattle.getA1(), oldBattle.getA2(), oldBattle.getB1(), oldBattle.getB2()};
            List<Integer> battlers = new ArrayList<>();
            if (null != oldBattle.getA1()) battlers.add(oldBattle.getA1());
            if (null != oldBattle.getA2()) battlers.add(oldBattle.getA2());
            if (null != oldBattle.getB1()) battlers.add(oldBattle.getB1());
            if (null != oldBattle.getB2()) battlers.add(oldBattle.getB2());
            memberDao.addRealPoint(battlers, oldBattle.getBattlePoint());

//            int[] voters = oldBattle.getVotes().stream().mapToInt(v -> v.getMemberId()).toArray();
            List<Integer> voters = (List<Integer>) oldBattle.getVotes().stream().map(v -> v.getMemberId()).collect(Collectors.toList());
            if (voters.size() > 0)
                memberDao.addRealPoint(voters, 1);
            //取消时，直接删除
            return battleDao.deleteByPrimaryKey(new Integer[]{battle.getBattleId()});
        }
        return battleDao.updateByPrimaryKeySelective(battle);
    }

    @Override
    @Transactional
    public int doCall(SunBattleVote vote) {
        //扣掉打CALL者1点实时积分
        memberDao.addRealPoint(Collections.singletonList(vote.getMemberId()), -1);
        return voteDao.insert(vote);
    }

    @Override
    @Transactional
    public int accept(SunBattle battle, Integer accepter) {
        //扣掉应战者3点或1点实时积分
        memberDao.addRealPoint(Collections.singletonList(accepter), -battle.getBattlePoint());
        return battleDao.updateByPrimaryKeySelective(battle);
    }

    @Override
    @Transactional
    public int quit(Integer battleId, String position, Integer quiter, Integer battlePoint) {
        //返回退出者3点实时积分
        memberDao.addRealPoint(Collections.singletonList(quiter), battlePoint);
        return battleDao.quit(battleId, position);
    }

    @Override
    public List<SunBattle> selectNotFormedBlindBattles(String date) {
        return battleDao.selectNotFormedBlindBattles(date);
    }

    @Override
    public boolean hasNotCompletedBlindBattles(String battleDate) {
        return battleDao.hasNotCompletedBlindBattles(battleDate);
    }

    @Override
    public int getBattleState(Integer battleId) {
        return battleDao.getBattleState(battleId);
    }

    @Override
    public List<SunBattle> selectBattlesFromNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String now = LocalDate.now().format(formatter);
        List<SunBattle> battles = battleDao.selectBattlesFromNow(now);
        //计算每个battle的A,B两队的支持数
        for (SunBattle battle : battles) {
            if (battle.getVotes().size() > 0) {
                battle.setVoteA((int) battle.getVotes().stream().filter(v -> v.getVote() == 1).count());
                battle.setVoteB((int) battle.getVotes().stream().filter(v -> v.getVote() == -1).count());
            }
        }
        return battles;
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
    public boolean hasNotCompletedBattlesByMemberId(Integer memberId) {
        return battleDao.hasNotCompletedBattlesByMemberId(memberId);
    }
}
