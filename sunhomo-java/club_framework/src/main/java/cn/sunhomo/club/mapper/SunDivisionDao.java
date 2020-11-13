package cn.sunhomo.club.mapper;

import cn.sunhomo.club.domain.SunDivision;

import java.util.List;

public interface SunDivisionDao {
    int deleteByPrimaryKey(int[] divisionId);

    int insert(SunDivision record);

    int insertSelective(SunDivision record);

    SunDivision selectByPrimaryKey(Integer divisionId);

    List<SunDivision> selectByActivityId(Integer activityId);

    int updateByPrimaryKeySelective(SunDivision record);

    int updateByPrimaryKey(SunDivision record);
}