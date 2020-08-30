package com.sunhomo.enroll.mapper;

import com.sunhomo.enroll.domain.SunDivision;

import java.util.List;

public interface SunDivisionDao {
    int deleteByPrimaryKey(Integer divisionId);

    int insert(SunDivision record);

    int insertSelective(SunDivision record);

    SunDivision selectByPrimaryKey(Integer divisionId);

    List<SunDivision> selectByActivityId(String activityId);

    int updateByPrimaryKeySelective(SunDivision record);

    int updateByPrimaryKey(SunDivision record);
}