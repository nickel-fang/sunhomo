package cn.sunhomo.club.service;

import cn.sunhomo.club.domain.SunGood;
import cn.sunhomo.club.domain.SunGoodTransaction;
import cn.sunhomo.club.domain.SunMember;

import java.util.List;

public interface ISunGoodService {
    SunGood selectByPrimaryKey(Integer goodId);

    List<SunGood> getAllGoods();

    void redeem(SunGood goods, SunMember member);

    int consign(SunGoodTransaction goodTransaction);

    List<SunGoodTransaction> selectRedeems(Integer goodTransactionState, Integer limit);
}
