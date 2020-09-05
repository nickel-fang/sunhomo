package com.sunhomo.enroll.service;

import com.sunhomo.enroll.domain.SunMember;

import java.util.List;

public interface ISunMemberService {
    public SunMember selectMember(Integer memberId);

    public SunMember selectMember(String openId);

    public int updateMember(SunMember member);

    public List<SunMember> selectMembers(SunMember member);

    public int deleteMember(String ids);

    public int insertMember(SunMember member);
}
