package cn.sunhomo.club.service.impl;

import cn.sunhomo.club.domain.SunMember;
import cn.sunhomo.club.domain.SunPointRecord;
import cn.sunhomo.club.mapper.SunMemberDao;
import cn.sunhomo.club.mapper.SunPointRecordDao;
import cn.sunhomo.club.service.ISunPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SunPointService implements ISunPointService {
    @Autowired
    private SunMemberDao memberDao;
    @Autowired
    private SunPointRecordDao pointRecordDao;

    @Override
    @Transactional
    public void updateMembersPoint(List<SunMember> updateMembers, List<SunPointRecord> insertPointRecords) {
        memberDao.batchUpdate(updateMembers);
        pointRecordDao.batchInsert(insertPointRecords);
//        for (SunPointRecord pointRecord : insertPointRecords) {
//            pointRecordDao.insert(pointRecord);
//        }
    }
}
