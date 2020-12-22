package cn.sunhomo.club.service.impl;

import cn.sunhomo.club.domain.SunGood;
import cn.sunhomo.club.domain.SunGoodTransaction;
import cn.sunhomo.club.domain.SunMember;
import cn.sunhomo.club.domain.SunPointRecord;
import cn.sunhomo.club.mapper.SunGoodDao;
import cn.sunhomo.club.mapper.SunGoodTransactionDao;
import cn.sunhomo.club.mapper.SunMemberDao;
import cn.sunhomo.club.mapper.SunPointRecordDao;
import cn.sunhomo.club.service.ISunGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SunGoodService implements ISunGoodService {

    @Autowired
    private SunGoodDao goodDao;

    @Autowired
    private SunMemberDao memberDao;

    @Autowired
    private SunGoodTransactionDao goodTransactionDao;

    @Autowired
    private SunPointRecordDao pointRecordDao;

    @Override
    public SunGood selectByPrimaryKey(Integer goodId) {
        return goodDao.selectByPrimaryKey(goodId);
    }

    @Override
    public List<SunGood> getAllGoods() {
        return goodDao.selectAllGoods();
    }

    @Override
    @Transactional
    public void redeem(SunGood goods, SunMember member) {
        //TODO 扣除个人积分，增加商品兑换记录， 修改商品兑换次数
        goods.setStock(goods.getStock() - 1);
        goods.setExchange(goods.getExchange() + 1);
        goodDao.updateByPrimaryKey(goods);

        SunMember newMember = new SunMember();
        newMember.setMemberId(member.getMemberId());
        newMember.setPoint(member.getPoint());
        memberDao.updateByPrimaryKeySelective(newMember);

        SunGoodTransaction goodTransaction = new SunGoodTransaction(null, goods.getGoodId(), member.getMemberId(), new Date());
        goodTransactionDao.insert(goodTransaction);

        SunPointRecord pointRecord = new SunPointRecord(null, member.getMemberId(), (byte) 4, "兑换商品：" + goods.getGoodName(), -goods.getValue(), new Date());
        pointRecordDao.insert(pointRecord);
    }

}
