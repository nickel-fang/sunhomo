package cn.sunhomo.club.mapper;

import cn.sunhomo.club.domain.SunGoodTransaction;

public interface SunGoodTransactionDao {
    int deleteByPrimaryKey(Integer goodTransactionId);

    int insert(SunGoodTransaction record);

    int insertSelective(SunGoodTransaction record);

    SunGoodTransaction selectByPrimaryKey(Integer goodTransactionId);

    int updateByPrimaryKeySelective(SunGoodTransaction record);

    int updateByPrimaryKey(SunGoodTransaction record);
}