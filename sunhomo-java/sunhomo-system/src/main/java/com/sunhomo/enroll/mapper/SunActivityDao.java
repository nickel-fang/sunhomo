package com.sunhomo.enroll.mapper;

import com.sunhomo.enroll.domain.SunActivity;
import sun.security.provider.Sun;

import java.util.List;

public interface SunActivityDao {
    int deleteByPrimaryKey(Integer[] ids);

    int insert(SunActivity record);

    int insertSelective(SunActivity record);

    SunActivity selectByPrimaryKey(Integer activityId);

    List<SunActivity> selectActivities(SunActivity activity);

    int updateByPrimaryKeySelective(SunActivity record);

    int updateByPrimaryKey(SunActivity record);
}