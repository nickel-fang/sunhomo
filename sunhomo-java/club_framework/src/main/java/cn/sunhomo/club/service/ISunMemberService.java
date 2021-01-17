package cn.sunhomo.club.service;

import cn.sunhomo.club.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISunMemberService {
    public SunMember selectMember(Integer memberId);

    public SunMember selectMember(String openId);

    public int updateMember(SunMember member);

    public List<SunMember> selectMembers(SunMember member);

    public int deleteMember(String ids);

    public int insertMember(SunMember member);

    List<SunPointRecord> getPointRecordsByMemberID(Integer memberId);

    List<SunActivity> getActivitiesByMemberID(Integer memberId);

    List<SunGoodTransaction> getGoodTransactionsByMemberID(Integer memberId);

    List<SunMember> getTop10YearPointByMember(SunMember member);

    List<SunPointRecord> getYearPointRecordsByOpenID(String openid);

    public void addRealPoint(int[] memberIds, int point);

    List<SunBattle> getBattlesByMemberID(Integer memberId);
}
