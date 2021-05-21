package cn.sunhomo.club.service;

import cn.sunhomo.club.domain.SunFinance;

import java.util.List;

/**
 * @author: Nickel Fang
 * @date: 2021/2/1 10:38
 */
public interface ISunFinanceService {


    int deleteByPrimaryKey(Integer financeId);

    int insert(SunFinance record);

    List<SunFinance> selectFinances(String year);

    Integer selectSumOfFinanes(String year);
}
