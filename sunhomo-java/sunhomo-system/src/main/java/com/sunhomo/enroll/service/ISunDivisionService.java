package com.sunhomo.enroll.service;

import com.sunhomo.enroll.domain.SunDivision;

import java.util.List;

public interface ISunDivisionService {
    public int insertDivision(SunDivision division);

    public List<SunDivision> selectDivisions(Integer activityId);

    public int updateDivision(SunDivision division);

    public int deleteByPrimaryKey(Integer[] ids);
}
