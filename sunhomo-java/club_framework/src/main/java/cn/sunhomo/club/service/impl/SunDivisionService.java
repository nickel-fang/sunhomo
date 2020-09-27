package cn.sunhomo.club.service.impl;

import cn.sunhomo.club.domain.SunDivision;
import cn.sunhomo.club.mapper.SunDivisionDao;
import cn.sunhomo.club.service.ISunDivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SunDivisionService implements ISunDivisionService {
    @Autowired
    private SunDivisionDao divisionDao;

    @Override
    public int insertDivision(SunDivision division) {
        return divisionDao.insert(division);
    }

    @Override
    public List<SunDivision> selectDivisions(Integer activityId) {
        return divisionDao.selectByActivityId(activityId);
    }

    @Override
    public int updateDivision(SunDivision division) {
        return divisionDao.updateByPrimaryKeySelective(division);
    }

    @Override
    public int deleteByPrimaryKey(Integer[] ids) {
        return divisionDao.deleteByPrimaryKey(ids);
    }
}
