package cn.sunhomo.club.mapper;

import cn.sunhomo.club.domain.SunAd;

import java.util.List;

public interface SunAdDao {

    int insert(SunAd record);

    SunAd selectByPrimaryKey(Integer adId);

    List<SunAd> selectAllOnShow();
}