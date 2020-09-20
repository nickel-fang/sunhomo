package com.sunhomo.enroll.service;

import com.sunhomo.enroll.domain.SunActivity;
import com.sunhomo.enroll.domain.SunMember;

import java.util.List;

public interface ISunActivityService {
    public List<SunActivity> selectActivities(SunActivity activity);

    public SunActivity selectActivity(Integer activityId);

    public int deleteActivityById(String ids);

    public int insertActivity(SunActivity activity);

    public int updateActivity(SunActivity activity);

    public int enroll(Integer activityId, SunMember member);
}
