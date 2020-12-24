package cn.sunhomo.club.service.impl;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunGoodTransaction;
import cn.sunhomo.club.domain.SunMember;
import cn.sunhomo.club.domain.SunPointRecord;
import cn.sunhomo.club.mapper.SunMemberDao;
import cn.sunhomo.club.service.ISunMemberService;
import cn.sunhomo.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        int[] memberIds = StringUtils.toIntArray(ids, ",");
        return memberDao.deleteByPrimaryKey(memberIds);
    }

    @Override
    public int insertMember(SunMember member) {
        return memberDao.insertSelective(member);
    }

    @Override
    public List<SunPointRecord> getPointRecordsByMemberID(Integer memberId) {
        return memberDao.getPointRecordsByMemberID(memberId);
    }

    @Override
    public List<SunActivity> getActivitiesByMemberID(Integer memberId) {
        return memberDao.getActivitiesByMemberID(memberId);
    }

    @Override
    public List<SunGoodTransaction> getGoodTransactionsByMemberID(Integer memberId) {
        return memberDao.getGoodTransactionsByMemberID(memberId);
    }

    @Override
    public List<SunMember> getTop10YearPointByMember(SunMember me) {
        List<SunMember> members = memberDao.getTop10YearPointByOpenID(me.getOpenid());
        int yearPoint = members.get(0).getYearPoint();
        int range = 1;
        for (SunMember member : members) {
            if (member.getYearPoint() == yearPoint) {
                //与前面的年度积分一样，并列排名
                member.setMemberId(range);
            } else {
                yearPoint = member.getYearPoint();
                range = member.getMemberId();
            }
        }
        return members;
    }
}
