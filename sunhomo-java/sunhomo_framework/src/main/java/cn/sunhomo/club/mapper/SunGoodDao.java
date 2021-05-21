package cn.sunhomo.club.mapper;

import cn.sunhomo.club.domain.SunGood;

import java.util.List;

public interface SunGoodDao {
    int deleteByPrimaryKey(Integer goodId);

    int insert(SunGood record);

    int insertSelective(SunGood record);

    SunGood selectByPrimaryKey(Integer goodId);

    int updateByPrimaryKeySelective(SunGood record);

    int updateByPrimaryKey(SunGood record);

    List<SunGood> selectAllGoods();
}