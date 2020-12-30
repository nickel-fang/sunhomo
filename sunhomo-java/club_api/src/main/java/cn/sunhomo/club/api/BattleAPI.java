package cn.sunhomo.club.api;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunBattle;
import cn.sunhomo.club.service.ISunActivityService;
import cn.sunhomo.club.service.ISunBattleService;
import cn.sunhomo.core.AjaxResult;
import cn.sunhomo.core.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/battle")
    @ResponseBody
    public AjaxResult<SunBattle> battle(@RequestBody SunBattle battle) {
        int result = battleService.insertBattle(battle);
        return result == 1 ? AjaxResult.success(battle) : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR, battle);
    }

    @PostMapping("/cancel/{battleId}")
    @ResponseBody
    public AjaxResult<SunActivity> cancel(@PathVariable("battleId") Integer battleId) {
        SunBattle battle = battleService.selectByPrimaryKey(battleId);
        int result = battleService.cancelBattle(battle);
        return result == 1 ? AjaxResult.success(activityService.selectActivity(battle.getActivityId())) : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR, activityService.selectActivity(battle.getActivityId()));
    }
}
