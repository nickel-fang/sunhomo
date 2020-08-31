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
    public List<SunActivity> selectActivities(SunActivity activity) {
        return activityDao.selectActivities(activity);
    }

    @Override
    public SunActivity selectActivity(Integer activityId) {
        return activityDao.selectByPrimaryKey(activityId);
    }

    @Override
    public int deleteActivityById(Integer activityId) {
        return activityDao.deleteByPrimaryKey(activityId);
    }

    @Override
    public int insertActivity(SunActivity activity) {
        return activityDao.insert(activity);
    }
}
