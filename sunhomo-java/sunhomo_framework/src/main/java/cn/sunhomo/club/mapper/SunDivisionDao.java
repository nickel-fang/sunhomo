package cn.sunhomo.club.mapper;

import cn.sunhomo.club.domain.SunDivision;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SunDivisionDao {
    int deleteByPrimaryKey(int[] ids);

    int insert(SunDivision record);

    int insertSelective(SunDivision record);

    SunDivision selectByPrimaryKey(Integer divisionId);

    List<SunDivision> selectByActivityId(Integer activityId);

    int updateByPrimaryKeySelective(SunDivision record);

    int updateByPrimaryKey(SunDivision record);

    int insertMemberToDivision(@Param("memberId") Integer memberId, @Param("divisionId") Integer activityId);

}