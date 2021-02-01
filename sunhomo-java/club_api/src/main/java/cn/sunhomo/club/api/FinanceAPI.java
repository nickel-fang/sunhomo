package cn.sunhomo.club.api;

import cn.sunhomo.club.domain.SunFinance;
import cn.sunhomo.club.service.ISunFinanceService;
import cn.sunhomo.core.AjaxResult;
import cn.sunhomo.core.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Nickel Fang
 * @date: 2021/2/1 10:41
 */
@Controller
@RequestMapping("/club/finance")
public class FinanceAPI {
    @Autowired
    private ISunFinanceService financeService;

    @PostMapping("/getFinance")
    @ResponseBody
    public Map<String, Object> getFinance() {
        int year = LocalDateTime.now().getYear();
        return getFinance(String.valueOf(year));
    }

    @PostMapping("insertFinance")
    @ResponseBody
    public AjaxResult<Map<String, Object>> insertFinance(@RequestBody SunFinance finance) {
        if (finance.getFinType() == 2) finance.setFinValue(-finance.getFinValue());
        int success = financeService.insert(finance);
        return success == 1 ? AjaxResult.success() : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    @PostMapping("deleteFinance")
    @ResponseBody
    public AjaxResult<Map<String, Object>> deleteFinance(SunFinance finance) {
        int success = financeService.deleteByPrimaryKey(finance.getFinanceId());
        return success == 1 ? AjaxResult.success() : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    private Map<String, Object> getFinance(String year) {
        Map<String, Object> result = new HashMap<>();
        result.put("sum", financeService.selectSumOfFinanes(year));
        result.put("records", financeService.selectFinances(year));
        return result;
    }
}
