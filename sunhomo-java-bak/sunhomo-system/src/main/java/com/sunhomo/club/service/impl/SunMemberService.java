package com.sunhomo.club.service.impl;

import com.sunhomo.common.core.text.Convert;
import com.sunhomo.club.domain.SunMember;
import com.sunhomo.club.mapper.SunMemberDao;
import com.sunhomo.club.service.ISunMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SunMemberService implements ISunMemberService {
    @Autowired
    private SunMemberDao memberDao;

    @Override
    public SunMember selectMember(Integer memberId) {
        return memberDao.selectByPrimaryKey(memberId);
    }

    @Override
    public SunMember selectMember(String openId) {
        return memberDao.selectByOpenId(openId);
    }

    @Override
    public int updateMember(SunMember member) {
        return memberDao.updateByPrimaryKeySelective(member);
    }

    @Override
    public List<SunMember> selectMembers(SunMember member) {
        return memberDao.selectMembers(member);
    }

    @Override
    public int deleteMember(String ids) {
        Integer[] memberIds = Convert.toIntArray(ids);
        return memberDao.deleteByPrimaryKey(memberIds);
    }

    @Override
    public int insertMember(SunMember member) {
        return memberDao.insertSelective(member);
    }
}
