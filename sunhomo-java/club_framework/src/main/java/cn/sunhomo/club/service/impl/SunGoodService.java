package cn.sunhomo.club.service.impl;

import cn.sunhomo.club.domain.SunGood;
import cn.sunhomo.club.mapper.SunGoodDao;
import cn.sunhomo.club.service.ISunGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SunGoodService implements ISunGoodService {

    @Autowired
    private SunGoodDao goodDao;

    @Override
    public List<SunGood> getAllGoods() {
        return goodDao.selectAllGoods();
    }
}
