package cn.sunhomo.club.mapper;

import cn.sunhomo.club.domain.SunActivity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SunActivityDao {
    int deleteByPrimaryKey(int[] ids);

    int insert(SunActivity record);

    int insertSelective(SunActivity record);

    SunActivity selectByPrimaryKey(Integer activityId);

    List<SunActivity> selectActivities(SunActivity activity);

    int updateByPrimaryKeySelective(SunActivity record);

    int updateByPrimaryKey(SunActivity record);

    List<Byte> selectCount(@Param("activityId") Integer activityId, @Param("memberId") Integer memberId);

    int insertMemberToActivity(@Param("memberId") Integer memberId, @Param("activityId") Integer activityId, @Param("enrollTime") Date enrollTime, @Param("isMaster") Byte isMaster);

    int deleteMemberToActivity(@Param("activityId") Integer activityId, @Param("memberId") Integer memberId, @Param("isMaster") Byte isMaster);

    List<SunActivity> getActivitiesForBattle(Integer memberId);

    SunActivity selectPreActivity(String activityDate);
}