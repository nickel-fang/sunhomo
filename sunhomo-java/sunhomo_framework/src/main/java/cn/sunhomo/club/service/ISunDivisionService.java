package cn.sunhomo.club.service;

import cn.sunhomo.club.domain.SunDivision;

import java.util.List;

public interface ISunDivisionService {
    public int insertDivision(SunDivision division);

    public List<SunDivision> selectDivisions(Integer activityId);

    public SunDivision selectDivision(Integer divisionId);

    public int updateDivision(SunDivision division);

    public int deleteDivisionById(String ids);

    void draw(Integer memberId, Integer divisionId);
}
