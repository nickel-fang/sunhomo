package cn.sunhomo.club.mapper;

import cn.sunhomo.club.domain.SunGoodTransaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SunGoodTransactionDao {
    int deleteByPrimaryKey(Integer goodTransactionId);

    int insert(SunGoodTransaction record);

    int insertSelective(SunGoodTransaction record);

    SunGoodTransaction selectByPrimaryKey(Integer goodTransactionId);

    int updateByPrimaryKeySelective(SunGoodTransaction record);

    int updateByPrimaryKey(SunGoodTransaction record);

    List<SunGoodTransaction> selectRedeems(@Param("goodTransactionState") Integer goodTransactionState, @Param("limit") Integer limit);
}