package cn.sunhomo.club.service.impl;

import cn.sunhomo.club.mapper.SunFinanceDao;
import cn.sunhomo.club.service.ISunFinanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import cn.sunhomo.club.domain.SunFinance;

import java.util.List;

/**
 * @author: Nickel Fang
 * @date: 2021/2/1 10:33
 */
@Service
public class SunFinanceService implements ISunFinanceService {

    @Resource
    private SunFinanceDao financeDao;

    @Override
    public int deleteByPrimaryKey(Integer financeId) {
        return financeDao.deleteByPrimaryKey(financeId);
    }

    @Override
    public int insert(SunFinance record) {
        return financeDao.insert(record);
    }

    @Override
    public List<SunFinance> selectFinances(String year) {
        return financeDao.selectFinances(year + "-01-01", year + "-12-31");
    }

    @Override
    public Integer selectSumOfFinanes(String year) {
        return financeDao.selectSumOfFinances(year + "-01-01", year + "-12-31");
    }

}

