package cn.sunhomo.club.api;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunGood;
import cn.sunhomo.club.domain.SunMember;
import cn.sunhomo.club.service.ISunGoodService;
import cn.sunhomo.core.AjaxResult;
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
    public AjaxResult<Integer> redeem(@PathVariable("goodId") Integer goodId, @RequestBody SunMember member) {
        //TODO 扣除个人积分，增加商品兑换记录， 修改商品兑换次数
        return AjaxResult.success();
    }
}
