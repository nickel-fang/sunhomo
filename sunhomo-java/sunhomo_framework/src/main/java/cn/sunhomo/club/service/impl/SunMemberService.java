package cn.sunhomo.club.service.impl;

import cn.sunhomo.club.domain.*;
import cn.sunhomo.club.mapper.SunMemberDao;
import cn.sunhomo.club.service.ISunMemberService;
import cn.sunhomo.util.StringUtils;
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
    public List<SunPointRecord> getYearPointRecordsByOpenID(String openid) {
        return memberDao.getYearPointRecordsByOpenID(openid);
    }

    public void addRealPoint(List<Integer> memberIds, int point) {
        memberDao.addRealPoint(memberIds, point);
    }

    @Override
    public List<SunBattle> getBattlesByMemberID(Integer memberId) {
        return memberDao.getBattlesByMemberID(memberId);
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

    @Override
    public List<SunMember> getTop10WinRatioByMember(SunMember me) {
        List<SunMember> members = memberDao.getTop10WinRatioByOpenID(me.getOpenid());
        if (members.size() > 0) {
            int ratio = members.get(0).getRatio();
            int range = 1;
            for (SunMember member : members) {
                if (member.getRatio() == ratio) {
                    //与前面的胜率一样，并列排名
                    member.setMemberId(range);
                } else {
                    ratio = member.getRatio();
                    range = member.getMemberId();
                }
            }
        }
        return members;
    }
}
