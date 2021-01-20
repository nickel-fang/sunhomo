package cn.sunhomo.club.mapper;

import cn.sunhomo.club.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SunMemberDao {
    int deleteByPrimaryKey(int[] memberIds);

    int insert(SunMember record);

    int insertSelective(SunMember record);

    SunMember selectByPrimaryKey(Integer memberId);

    SunMember selectByOpenId(String openId);

    List<SunMember> selectMembersByActivityId(Integer activityId);

    List<SunMember> selectMembersByDivisionId(Integer divisionId);

    List<SunMember> selectMembers(SunMember member);

    int updateByPrimaryKeySelective(SunMember record);

    int updateByPrimaryKey(SunMember record);

    void batchUpdate(List<SunMember> updateMembers);

    List<SunPointRecord> getPointRecordsByMemberID(Integer memberId);

    List<SunActivity> getActivitiesByMemberID(Integer memberId);

    List<SunGoodTransaction> getGoodTransactionsByMemberID(Integer memberId);

    List<SunMember> getTop10YearPointByOpenID(String openId);

    void addRealPoint(@Param("memberIds") List<Integer> memberIds, @Param("point") int point);

    List<SunPointRecord> getYearPointRecordsByOpenID(String openid);

    List<SunBattle> getBattlesByMemberID(Integer memberId);
}