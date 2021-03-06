package cn.sunhomo.club.api;

import cn.sunhomo.club.domain.SunGood;
import cn.sunhomo.club.domain.SunGoodTransaction;
import cn.sunhomo.club.domain.SunMember;
import cn.sunhomo.club.service.ISunGoodService;
import cn.sunhomo.club.service.ISunMemberService;
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
    @Autowired
    private ISunMemberService memberService;

    @PostMapping("/list")
    @ResponseBody
    public List<SunGood> list() {
        List<SunGood> list = goodService.getAllGoods();
        return list;
    }

    //兑换商品
    @PostMapping("/redeem/{goodId}")
    @ResponseBody
    public AjaxResult<SunMember> redeem(@PathVariable("goodId") Integer goodId, @RequestBody SunMember member) {
        //防止客户端暂存了用户积分，需要重新获取用户，刷新下积分
        SunMember realMember = memberService.selectMember(member.getMemberId());
        SunGood goods = goodService.selectByPrimaryKey(goodId);
        if (goods.getValue() > realMember.getPoint()) {
            return AjaxResult.failure(ResultCode.POINT_NOT_ENOUGH);
        }
        realMember.setPoint(realMember.getPoint() - goods.getValue());
        goodService.redeem(goods, realMember);
        return AjaxResult.success(realMember);
    }

    //交付商品
    @PostMapping("/consign/{goodTransactionId}")
    @ResponseBody
    public AjaxResult<List<SunGoodTransaction>> consign(@PathVariable("goodTransactionId") Integer goodTransactionId) {
        SunGoodTransaction goodTransaction = new SunGoodTransaction();
        goodTransaction.setGoodTransactionId(goodTransactionId);
        goodTransaction.setState((byte) 2);
        int result = goodService.consign(goodTransaction);
        return result == 1 ? AjaxResult.success(goodService.selectRedeems(1, null)) : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR, goodService.selectRedeems(1, null));
    }

    //获取兑换列表
    @PostMapping("/getRedeems/{goodTransactionState}")
    @ResponseBody
    public List<SunGoodTransaction> getRedeems(@PathVariable("goodTransactionState") Integer goodTransactionState) {
        Integer limit = null;
        if (goodTransactionState == 2) limit = 20;
        return goodService.selectRedeems(goodTransactionState, limit);
    }
}
