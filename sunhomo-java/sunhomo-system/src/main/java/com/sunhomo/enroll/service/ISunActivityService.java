package com.sunhomo.enroll.service;

import com.sunhomo.enroll.domain.SunActivity;

import java.util.List;

public interface ISunActivityService {
    public List<SunActivity> selectActivities(Byte activityState);

    public SunActivity selectActivity(Integer activityId);
}
