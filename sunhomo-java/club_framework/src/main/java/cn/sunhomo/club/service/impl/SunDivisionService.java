package cn.sunhomo.club.service.impl;

import cn.sunhomo.club.domain.SunDivision;
import cn.sunhomo.club.mapper.SunDivisionDao;
import cn.sunhomo.club.service.ISunDivisionService;
import cn.sunhomo.util.StringUtils;
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
    public int deleteDivisionById(String ids) {
        int[] divisionIds = StringUtils.toIntArray(ids, ",");
        return divisionDao.deleteByPrimaryKey(divisionIds);
    }

    @Override
    public void draw(Integer memberId, Integer divisionId) {
        divisionDao.insertMemberToDivision(memberId, divisionId);
    }
}
