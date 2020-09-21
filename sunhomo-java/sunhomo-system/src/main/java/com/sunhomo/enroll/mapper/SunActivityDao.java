package com.sunhomo.enroll.mapper;

import com.sunhomo.enroll.domain.SunActivity;
import com.sunhomo.enroll.domain.SunMember;
import org.apache.ibatis.annotations.Param;
import sun.security.provider.Sun;

import java.util.Date;
import java.util.List;

public interface SunActivityDao {
    int deleteByPrimaryKey(Integer[] ids);

    int insert(SunActivity record);

    int insertSelective(SunActivity record);

    SunActivity selectByPrimaryKey(Integer activityId);

    List<SunActivity> selectActivities(SunActivity activity);

    int updateByPrimaryKeySelective(SunActivity record);

    int updateByPrimaryKey(SunActivity record);

    List<Byte> selectCount(@Param("activityId") Integer activityId, @Param("memberId") Integer memberId);

    int insertMemberToActivity(@Param("memberId") Integer memberId, @Param("activityId") Integer activityId, @Param("enrollTime") Date enrollTime, @Param("isMaster") Byte isMaster);

    int deleteMemberToActivity(@Param("activityId") Integer activityId, @Param("memberId") Integer memberId, @Param("isMaster") Byte isMaster);
}