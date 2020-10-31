package cn.sunhomo.club.service;

import cn.sunhomo.club.domain.SunMember;
import cn.sunhomo.club.domain.SunPointRecord;

import java.util.List;

public interface ISunPointService {
    public void updateMembersPoint(List<SunMember> updateMembers, List<SunPointRecord> insertPointRecords);

}
