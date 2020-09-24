package com.sunhomo.club.service.impl;

import com.sunhomo.common.core.text.Convert;
import com.sunhomo.club.domain.SunActivity;
import com.sunhomo.club.domain.SunMember;
import com.sunhomo.club.mapper.SunActivityDao;
import com.sunhomo.club.service.ISunActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        Integer[] activityIds = Convert.toIntArray(ids);
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
    public int quit(Integer activityId, Byte isMaster, SunMember member) {
        return activityDao.deleteMemberToActivity(activityId, member.getMemberId(), isMaster);
    }
}
