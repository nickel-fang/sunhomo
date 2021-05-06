package cn.sunhomo.club.service.impl;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunBattle;
import cn.sunhomo.club.domain.SunBlind;
import cn.sunhomo.club.domain.SunMember;
import cn.sunhomo.club.mapper.SunActivityDao;
import cn.sunhomo.club.mapper.SunBattleDao;
import cn.sunhomo.club.mapper.SunBlindDao;
import cn.sunhomo.club.mapper.SunMemberDao;
import cn.sunhomo.club.service.ISunActivityService;
import cn.sunhomo.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SunActivityService implements ISunActivityService {
    @Autowired
    private SunActivityDao activityDao;

    @Autowired
    private SunBattleDao battleDao;

    @Autowired
    private SunMemberDao memberDao;

    @Autowired
    private SunBlindDao blindDao;

    @Override
    public List<SunActivity> selectActivities(SunActivity activity) {
        return activityDao.selectActivities(activity);
    }

    @Override
    public SunActivity selectActivity(Integer activityId) {
        return activityDao.selectByPrimaryKey(activityId);
    }

    @Override
    public int deleteActivityById(String ids) {
        int[] activityIds = StringUtils.toIntArray(ids, ",");
        return activityDao.deleteByPrimaryKey(activityIds);
    }

    @Override
    public int insertActivity(SunActivity activity) {
        return activityDao.insert(activity);
    }

    @Override
    public int updateActivity(SunActivity activity) {
        return activityDao.updateByPrimaryKeySelective(activity);
    }

    @Override
    public int enroll(Integer activityId, SunMember member) {
        //查出同一会员已报名序号
        List<Byte> num = activityDao.selectCount(activityId, member.getMemberId());
        byte index = 0;
        if (num.size() > 0) {
            if (num.get(num.size() - 1) == num.size() - 1) {
                index = (byte) num.size();
            } else {
                for (int i = 0; i < num.size(); i++) {
                    if (i != num.get(i)) {
                        index = (byte) i;
                        break;
                    }
                }
            }
        }
        return activityDao.insertMemberToActivity(member.getMemberId(), activityId, new Date(), index);
    }

    @Override
    @Transactional
    public int quit(SunActivity activity, Byte isMaster, Integer memberId) {
        if (activity.getActivityType() == 1) {
            //删除所报的盲盒池
            blindDao.deleteMemberByActivityId(activity.getActivityId(), memberId);

            List<SunBattle> battles = battleDao.selectBattlesByActivityIdAndMemberId(activity.getActivityId(), memberId, null);
            if (isMaster == 0) { //主报人退报，要判断是否有应战，有并取消（包括挂的约战）
                for (SunBattle battle : battles) {
                    if (battle.getA1() != null && battle.getA1().intValue() == memberId.intValue()) {
                        memberDao.addRealPoint(Collections.singletonList(memberId), battle.getBattlePoint());
                        battleDao.quit(battle.getBattleId(), "A1");
                    }
                    if (battle.getA2() != null && battle.getA2().intValue() == memberId.intValue()) {
                        memberDao.addRealPoint(Collections.singletonList(memberId), battle.getBattlePoint());
                        battleDao.quit(battle.getBattleId(), "A2");
                    }
                    if (battle.getB1() != null && battle.getB1().intValue() == memberId.intValue()) {
                        memberDao.addRealPoint(Collections.singletonList(memberId), battle.getBattlePoint());
                        battleDao.quit(battle.getBattleId(), "B1");
                    }
                    if (battle.getB2() != null && battle.getB2().intValue() == memberId.intValue()) {
                        memberDao.addRealPoint(Collections.singletonList(memberId), battle.getBattlePoint());
                        battleDao.quit(battle.getBattleId(), "B2");
                    }

                }
            } else if (isMaster == 1) { //挂1退报，只取消挂的约战
                for (SunBattle battle : battles) {
                    String position = null;
                    if (battle.getA1Name() != null && battle.getA1().intValue() == memberId.intValue() && StringUtils.contains(battle.getA1Name(), "+1")) {
                        memberDao.addRealPoint(Collections.singletonList(memberId), battle.getBattlePoint());
                        battleDao.quit(battle.getBattleId(), "A1");
                    } else if (battle.getA2() != null && battle.getA2().intValue() == memberId.intValue() && StringUtils.contains(battle.getA2Name(), "+1")) {
                        memberDao.addRealPoint(Collections.singletonList(memberId), battle.getBattlePoint());
                        battleDao.quit(battle.getBattleId(), "A2");
                    } else if (battle.getB1() != null && battle.getB1().intValue() == memberId.intValue() && StringUtils.contains(battle.getB1Name(), "+1")) {
                        memberDao.addRealPoint(Collections.singletonList(memberId), battle.getBattlePoint());
                        battleDao.quit(battle.getBattleId(), "B1");
                    } else if (battle.getB2() != null && battle.getB2().intValue() == memberId.intValue() && StringUtils.contains(battle.getB2Name(), "+1")) {
                        memberDao.addRealPoint(Collections.singletonList(memberId), battle.getBattlePoint());
                        battleDao.quit(battle.getBattleId(), "B2");
                    }
                }
            }
        }
        return activityDao.deleteMemberToActivity(activity.getActivityId(), memberId, isMaster);
    }

    @Override
//    @Transactional
    public int blindBattle(Integer activityId, Integer memberId) {
        //暂不扣，放在自动创建盲盒时再扣
//        memberDao.addRealPoint(Collections.singletonList(memberId), -1);
        return blindDao.insert(new SunBlind(memberId, activityId, new Date(), null));
    }

    @Override
    public List<Byte> selectCount(Integer activityId, Integer memberId) {
        return activityDao.selectCount(activityId, memberId);
    }

    @Override
    public List<SunActivity> getActivitiesForBattle(Integer memberId) {
        List<SunActivity> activitiesForBattle = activityDao.getActivitiesForBattle(memberId);
        for (SunActivity activity : activitiesForBattle) {
            //要求超过3积分
            //activity.setMembers(activity.getMembers().stream().limit(activity.getNumbers()).filter(m -> m.getIsMaster() == 0 && m.getPoint() >= 3).collect(Collectors.toList()));
            activity.setMembers(activity.getMembers().stream().limit(activity.getNumbers()).filter(m -> m.getPoint() >= 3).map(m -> {
                if (m.getIsMaster() != 0) m.setMemberName(m.getMemberName() + "+" + m.getIsMaster());
                return m;
            }).collect(Collectors.toList()));
        }
        return activitiesForBattle;
    }
}
