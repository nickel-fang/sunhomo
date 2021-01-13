package cn.sunhomo.club.api;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunBattle;
import cn.sunhomo.club.domain.SunBattleVote;
import cn.sunhomo.club.service.ISunActivityService;
import cn.sunhomo.club.service.ISunBattleService;
import cn.sunhomo.club.service.ISunMemberService;
import cn.sunhomo.core.AjaxResult;
import cn.sunhomo.core.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    @Autowired
    private ISunMemberService memberService;

    private static final Lock lock = new ReentrantLock();

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
        //活动已开始，不能约战 (暂不用判断，活动开始也可以发起约战，活动结束后1分钟将状态置为已结束，前台发起约战时获取不到该活动了）
        /*SunActivity activity = activityService.selectActivity(battle.getActivityId());
        if (LocalDateTime.now().isAfter(LocalDateTime.parse(activity.getActivityDate() + " " + activity.getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))) {
            return AjaxResult.failure(ResultCode.ACTIVITY_HAS_STARTED);
        }*/
        int result = battleService.insertBattle(battle);
        return result == 1 ? AjaxResult.success(memberService.selectMember(battle.getA1())) : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR, battle);
    }

    /**
     * 更新
     *
     * @param battle
     * @return
     */
    @PostMapping("/confirmAndCancelAndWin")
    @ResponseBody
    public AjaxResult<List<SunBattle>> confirmAndCancelAndWin(@RequestBody SunBattle battle) {
        int result = 0;
        try {
            lock.lock();
            result = battleService.confirmAndCancelAndWin(battle);
        } finally {
            lock.unlock();
        }
        return result == 1 ? AjaxResult.success(battleService.selectBattlesFromNow()) : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR, battleService.selectBattlesFromNow());
    }

    /**
     * 打CALL功能
     *
     * @param vote
     * @return
     */
    @PostMapping("/doCall")
    @ResponseBody
    public AjaxResult<List<SunBattle>> doCall(@RequestBody SunBattleVote vote) {
        //约战已取消（查不到记录），不能打CALL，返回要刷新
        SunBattle battle = battleService.selectByPrimaryKey(vote.getBattleId());
        if (null == battle)
            return AjaxResult.failure(ResultCode.BATTLE_HAS_CANCELLED, battleService.selectBattlesFromNow());

        //活动已开始，不能打CALL
        SunActivity activity = activityService.selectActivity(battle.getActivityId());
        if (LocalDateTime.now().isAfter(LocalDateTime.parse(activity.getActivityDate() + " " + activity.getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))) {
            return AjaxResult.failure(ResultCode.ACTIVITY_HAS_STARTED);
        }
        int result = 0;
        try {
            lock.lock();
            //在获锁的过程中，活动有可能被取消
            battle = battleService.selectByPrimaryKey(vote.getBattleId());
            if (null == battle)
                return AjaxResult.failure(ResultCode.BATTLE_HAS_CANCELLED, battleService.selectBattlesFromNow());
            result = battleService.doCall(vote);
        } finally {
            lock.unlock();
        }

        return result == 1 ? AjaxResult.success(battleService.selectBattlesFromNow()) : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR, battleService.selectBattlesFromNow());
    }

}
