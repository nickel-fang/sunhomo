package cn.sunhomo.club.service;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunMember;

import java.util.List;

public interface ISunActivityService {
    public List<SunActivity> selectActivities(SunActivity activity);

    public SunActivity selectActivity(Integer activityId);

    public int deleteActivityById(String ids);

    public int insertActivity(SunActivity activity);

    public int updateActivity(SunActivity activity);

    public int enroll(Integer activityId, SunMember member);

    int quit(SunActivity activity, Byte isMaster, Integer memberId);

    //某个活动，同一人（包括挂）所有的报名
    List<Byte> selectCount(Integer activityId, Integer memberId);

    List<SunActivity> getActivitiesForBattle(Integer memberId);

//    int blindBattle(Integer activityId, Integer memberId);

    public boolean hasInBlindBox(Integer activityId, Integer memberId);

    public void enrollBlindBox(Integer activityId, Integer memberId);
}
