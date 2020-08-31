package com.sunhomo.enroll.service.impl;

import com.sunhomo.enroll.domain.SunActivity;
import com.sunhomo.enroll.mapper.SunActivityDao;
import com.sunhomo.enroll.service.ISunActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SunActivityService implements ISunActivityService {
    @Autowired
    private SunActivityDao activityDao;

    @Override
    public List<SunActivity> selectActivities(Byte activityState) {
        return activityDao.selectActivities(activityState);
    }

    @Override
    public SunActivity selectActivity(Integer activityId) {
        return activityDao.selectByPrimaryKey(activityId);
    }
}
