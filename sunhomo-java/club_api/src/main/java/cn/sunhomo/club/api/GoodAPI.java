package cn.sunhomo.club.api;

import cn.sunhomo.club.domain.SunGood;
import cn.sunhomo.club.domain.SunMember;
import cn.sunhomo.club.service.ISunGoodService;
import cn.sunhomo.core.AjaxResult;
import cn.sunhomo.core.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/club/good")
@Slf4j
public class GoodAPI {
    @Autowired
    private ISunGoodService goodService;

    @PostMapping("/list")
    @ResponseBody
    public List<SunGood> list() {
        List<SunGood> list = goodService.getAllGoods();
        return list;
    }

    @PostMapping("/redeem/{goodId}")
    @ResponseBody
    public AjaxResult<SunMember> redeem(@PathVariable("goodId") Integer goodId, @RequestBody SunMember member) {
        SunGood goods = goodService.selectByPrimaryKey(goodId);
        if (goods.getValue() > member.getPoint()) {
            return AjaxResult.failure(ResultCode.POINT_NOT_ENOUGH);
        }
        member.setPoint(member.getPoint() - goods.getValue());
        goodService.redeem(goods, member);
        return AjaxResult.success(member);
    }
}
