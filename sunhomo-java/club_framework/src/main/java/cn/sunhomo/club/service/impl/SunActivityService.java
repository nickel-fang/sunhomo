package cn.sunhomo.club.service.impl;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunMember;
import cn.sunhomo.club.mapper.SunActivityDao;
import cn.sunhomo.club.service.ISunActivityService;
import cn.sunhomo.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.provider.Sun;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SunActivityService implements ISunActivityService {
    @Autowired
    private SunActivityDao activityDao;

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
    public int quit(Integer activityId, Byte isMaster, Integer memberId) {
        return activityDao.deleteMemberToActivity(activityId, memberId, isMaster);
    }

    @Override
    public List<Byte> selectCount(Integer activityId, Integer memberId) {
        return activityDao.selectCount(activityId, memberId);
    }

    @Override
    public List<SunActivity> getActivitiesForBattle(Integer memberId) {
        List<SunActivity> activitiesForBattle = activityDao.getActivitiesForBattle(memberId);
        for (SunActivity activity : activitiesForBattle) {
            //需要过滤掉挂和替补，同时有超过3积分的
            activity.setMembers(activity.getMembers().stream().limit(activity.getNumbers()).filter(m -> m.getIsMaster() == 0).filter(m -> m.getPoint() >= 3).collect(Collectors.toList()));
            //只需过滤掉替补
            /*activity.setMembers(activity.getMembers().stream().limit(activity.getNumbers()).map(m -> {
                if (m.getIsMaster() != 0) m.setMemberName(m.getMemberName() + "+" + m.getIsMaster());
                return m;
            }).collect(Collectors.toList()));*/
        }
        return activitiesForBattle;
    }
}
