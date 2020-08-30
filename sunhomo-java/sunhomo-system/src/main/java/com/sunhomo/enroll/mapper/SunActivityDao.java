package com.sunhomo.enroll.mapper;

import com.sunhomo.enroll.domain.SunActivity;

import java.util.List;

public interface SunActivityDao {
    int deleteByPrimaryKey(Integer activityId);

    int insert(SunActivity record);

    int insertSelective(SunActivity record);

    SunActivity selectByPrimaryKey(Integer activityId);

    List<SunActivity> selectActivities(Byte activityState);

    int updateByPrimaryKeySelective(SunActivity record);

    int updateByPrimaryKey(SunActivity record);
}