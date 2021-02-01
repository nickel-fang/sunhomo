package cn.sunhomo.club.mapper;

import cn.sunhomo.club.domain.SunFinance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Nickel Fang
 * @date: 2021/2/1 10:38
 */
public interface SunFinanceDao {
    int deleteByPrimaryKey(Integer financeId);

    int insert(SunFinance record);

    List<SunFinance> selectFinances(@Param("startDate") String startDate, @Param("endDate") String endDate);

    Integer selectSumOfFinances(@Param("startDate") String startDate, @Param("endDate") String endDate);
}