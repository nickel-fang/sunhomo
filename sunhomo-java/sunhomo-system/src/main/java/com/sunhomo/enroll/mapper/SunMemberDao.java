package com.sunhomo.enroll.mapper;

import com.sunhomo.enroll.domain.SunMember;

import java.util.List;

public interface SunMemberDao {
    int deleteByPrimaryKey(Integer memberId);

    int insert(SunMember record);

    int insertSelective(SunMember record);

    SunMember selectByPrimaryKey(Integer memberId);

    SunMember selectByOpenId(String openId);

    List<SunMember> selectMembersByActivityId(Integer activityId);

    int updateByPrimaryKeySelective(SunMember record);

    int updateByPrimaryKey(SunMember record);
}