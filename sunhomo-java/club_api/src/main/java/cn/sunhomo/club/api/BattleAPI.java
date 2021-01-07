package cn.sunhomo.club.api;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunBattle;
import cn.sunhomo.club.service.ISunActivityService;
import cn.sunhomo.club.service.ISunBattleService;
import cn.sunhomo.core.AjaxResult;
import cn.sunhomo.core.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Nickel Fang
 * @date: 2020/12/30 14:55
 */
@RestController
@RequestMapping("/club/battle")
public class BattleAPI {
    @Autowired
    private ISunBattleService battleService;

    @Autowired
    private ISunActivityService activityService;


    /**
     * 获取约战列表 [今天, )
     *
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public List<SunBattle> list() {
        List<SunBattle> list = battleService.selectBattlesFromNow();
        return list;
    }

    //发起挑战
    @PostMapping("/battle")
    @ResponseBody
    public AjaxResult<SunBattle> battle(@RequestBody SunBattle battle) {
        int result = battleService.insertBattle(battle);
        return result == 1 ? AjaxResult.success(battle) : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR, battle);
    }

    /**
     * 更新
     * @param battle
     * @return
     */
    @PostMapping("/confirmAndCancelAndWin")
    @ResponseBody
    public AjaxResult<List<SunBattle>> confirmAndCancelAndWin(@RequestBody SunBattle battle) {
        int result = battleService.confirmAndCancelAndWin(battle);
        return result == 1 ? AjaxResult.success(battleService.selectBattlesFromNow()) : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR, battleService.selectBattlesFromNow());
    }

}
