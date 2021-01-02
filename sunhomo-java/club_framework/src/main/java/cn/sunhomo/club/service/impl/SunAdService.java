package cn.sunhomo.club.service.impl;

import cn.sunhomo.club.domain.SunAd;
import cn.sunhomo.club.mapper.SunAdDao;
import cn.sunhomo.club.service.ISunAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SunAdService implements ISunAdService {

    @Autowired
    private SunAdDao adDao;

    @Override
    public List<SunAd> getAds() {
        return adDao.selectAllOnShow();
    }
}
