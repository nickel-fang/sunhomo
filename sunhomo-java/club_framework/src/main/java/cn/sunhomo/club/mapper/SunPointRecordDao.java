package cn.sunhomo.club.mapper;

import cn.sunhomo.club.domain.SunPointRecord;

import java.util.List;

public interface SunPointRecordDao {
    int deleteByPrimaryKey(Integer pointRecordId);

    int insert(SunPointRecord record);

    int insertSelective(SunPointRecord record);

    SunPointRecord selectByPrimaryKey(Integer pointRecordId);

    int updateByPrimaryKeySelective(SunPointRecord record);

    int updateByPrimaryKey(SunPointRecord record);

    void batchInsert(List<SunPointRecord> insertPointRecords);
}