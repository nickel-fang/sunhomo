package cn.sunhomo.club.api;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunBattle;
import cn.sunhomo.club.domain.SunBattleVote;
import cn.sunhomo.club.domain.SunMember;
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
import java.util.stream.Collectors;

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

    //定义5个锁，用于不同battle之间分锁处理
    private static final Lock[] locks = {new ReentrantLock(), new ReentrantLock(), new ReentrantLock(), new ReentrantLock(), new ReentrantLock()};

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
        /*//每人每次不能超过三场约战
        if (battle.getA1() != null) {
            if (battleService.selectBattlesByActivityIdAndMemberId(battle.getActivityId(), battle.getA1()).size() >= 3)
                return AjaxResult.failure(ResultCode.BATTLE_HAS_ENOUGH_BATTLES);
        }
        if (battle.getA2() != null) {
            if (battleService.selectBattlesByActivityIdAndMemberId(battle.getActivityId(), battle.getA2()).size() >= 3)
                return AjaxResult.failure(ResultCode.BATTLE_OTHER_HAS_ENOUGH_BATTLES);
        }
        if (battle.getB1() != null) {
            if (battleService.selectBattlesByActivityIdAndMemberId(battle.getActivityId(), battle.getB1()).size() >= 3)
                return AjaxResult.failure(ResultCode.BATTLE_OTHER_HAS_ENOUGH_BATTLES);
        }
        if (battle.getB2() != null) {
            if (battleService.selectBattlesByActivityIdAndMemberId(battle.getActivityId(), battle.getB2()).size() >= 3)
                return AjaxResult.failure(ResultCode.BATTLE_OTHER_HAS_ENOUGH_BATTLES);
        }*/

        //单次活动不能超过10场约战
        if (battleService.selectBattlesCount(battle.getActivityId(), null, null, null) >= 10) {
            return AjaxResult.failure(ResultCode.ACTIVITY_HAS_ENOUGH_BATTLES);
        }

        //每人每次不能超过一场明战，一场暗战
        if (battle.getA1() != null) {
            if (battleService.selectBattlesCount(battle.getActivityId(), battle.getBattleId(), battle.getA1(), battle.getIsBlind()) >= 1)
                return AjaxResult.failure(ResultCode.BATTLE_A1_HAS_ENOUGH_BATTLES);
        }
        if (battle.getA2() != null) {
            if (battleService.selectBattlesCount(battle.getActivityId(), battle.getBattleId(), battle.getA2(), battle.getIsBlind()) >= 1)
                return AjaxResult.failure(ResultCode.BATTLE_A2_HAS_ENOUGH_BATTLES);
        }
        if (battle.getB1() != null) {
            if (battleService.selectBattlesCount(battle.getActivityId(), battle.getBattleId(), battle.getB1(), battle.getIsBlind()) >= 1)
                return AjaxResult.failure(ResultCode.BATTLE_B1_HAS_ENOUGH_BATTLES);
        }
        if (battle.getB2() != null) {
            if (battleService.selectBattlesCount(battle.getActivityId(), battle.getBattleId(), battle.getB2(), battle.getIsBlind()) >= 1)
                return AjaxResult.failure(ResultCode.BATTLE_B2_HAS_ENOUGH_BATTLES);
        }

        if (battle.getIsBlind() != null && battle.getIsBlind() == 1) {
            //活动当天不能发起盲盒约战
            if (DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()) == battle.getBattleDate())
                return AjaxResult.failure(ResultCode.BATTLE_BLIND_NOT_ALLOWED);
            if (battleService.hasNotCompletedBlindBattles(battle.getBattleDate()))
                return AjaxResult.failure(ResultCode.BATTLE_HAS_BLIND_NOT_COMPLETED);
        } /*else if (battleService.hasNotCompletedBattlesByMemberId(battle.getA1())) {
            return AjaxResult.failure(ResultCode.BATTLE_HAS_NOT_COMPLETED);
        }*/
        int result = battleService.insertBattle(battle);
        return result == 1 ? AjaxResult.success(memberService.selectMember(battle.getA1())) : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR, battle);
    }

    //本人是否有未成团的约战
    @PostMapping("/hasNotCompletedBattle")
    @ResponseBody
    public AjaxResult<Boolean> hasNotCompletedBattle(@RequestBody Integer memberId) {
        return AjaxResult.success(false);
        //现在限制了每个活动，每人只允许一场明战一场暗战。暂不需要此逻辑控制
        //return AjaxResult.success(battleService.hasNotCompletedBattlesByMemberId(memberId));
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
            locks[battle.getBattleId() % locks.length].lock();
            result = battleService.confirmAndCancelAndWin(battle);
        } finally {
            locks[battle.getBattleId() % locks.length].unlock();
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
            locks[battle.getBattleId() % locks.length].lock();
            //在获锁的过程中，活动有可能被取消
            battle = battleService.selectByPrimaryKey(vote.getBattleId());
            if (null == battle)
                return AjaxResult.failure(ResultCode.BATTLE_HAS_CANCELLED, battleService.selectBattlesFromNow());
            result = battleService.doCall(vote);
        } finally {
            locks[battle.getBattleId() % locks.length].unlock();
        }

        return result == 1 ? AjaxResult.success(battleService.selectBattlesFromNow()) : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR, battleService.selectBattlesFromNow());
    }

    /**
     * 应战
     *
     * @param battle
     * @return
     */
    @PostMapping("/accept")
    @ResponseBody
    public AjaxResult<List<SunBattle>> accept(@RequestBody SunBattle battle) {
        Integer accepter = null;
        String acceptPosition = null;
        //约战已取消（查不到记录），不能应战，返回要刷新
        SunBattle tempbattle = battleService.selectByPrimaryKey(battle.getBattleId());
        if (null == battle)
            return AjaxResult.failure(ResultCode.BATTLE_HAS_CANCELLED, battleService.selectBattlesFromNow());

        //约战人数已经满了
        if (tempbattle.getBattleState() == 2)
            return AjaxResult.failure(ResultCode.BATTLE_HAS_ENOUGH_MEMBER, battleService.selectBattlesFromNow());

        if (battle.getA1() != null) {
            accepter = battle.getA1();
            acceptPosition = "A1";
        }
        if (battle.getA2() != null) {
            accepter = battle.getA2();
            acceptPosition = "A2";
        }
        if (battle.getB1() != null) {
            accepter = battle.getB1();
            acceptPosition = "B1";
        }
        if (battle.getB2() != null) {
            accepter = battle.getB2();
            acceptPosition = "B2";
        }

        //是否报名了此活动（增加替补允许应战）
        SunActivity activity = activityService.selectActivity(tempbattle.getActivityId());
        Integer finalAccepter = accepter;
        if (activity.getMembers().stream().limit(activity.getNumbers()).noneMatch(m -> m.getMemberId().intValue() == finalAccepter.intValue() && m.getIsMaster() == 0))
            return AjaxResult.failure(ResultCode.BATTLE_HAS_NOT_ENROLLED, battleService.selectBattlesFromNow());

        List<SunMember> collectOfAccepters = activity.getMembers().stream().filter(m -> m.getMemberId().intValue() == finalAccepter.intValue()).collect(Collectors.toList());
        //每人每次不能超过一场明战，一场暗战（当前约战不受限制）
        if (battleService.selectBattlesCount(tempbattle.getActivityId(), battle.getBattleId(), accepter, tempbattle.getIsBlind()) >= 1)
            return AjaxResult.failure(ResultCode.BATTLE_HAS_ENOUGH_BATTLES);

        boolean hasAttended = false;
        if (tempbattle.getA1() != null && tempbattle.getA1().intValue() == accepter.intValue()) {
            hasAttended = true;
        } else if (tempbattle.getA2() != null && tempbattle.getA2().intValue() == accepter.intValue()) {
            hasAttended = true;
        } else if (tempbattle.getB1() != null && tempbattle.getB1().intValue() == accepter.intValue()) {
            hasAttended = true;
        } else if (tempbattle.getB2() != null && tempbattle.getB2().intValue() == accepter.intValue()) {
            hasAttended = true;
        }

        if (collectOfAccepters.size() == 1 && hasAttended) {
            return AjaxResult.failure(ResultCode.BATTLE_HAS_ATTENDED);
        } else if (collectOfAccepters.size() > 1 && hasAttended) {
            switch (acceptPosition) {
                case "A1":
                    battle.setA1Name(battle.getA1Name() + "+1");
                    break;
                case "A2":
                    battle.setA2Name(battle.getA2Name() + "+1");
                    break;
                case "B1":
                    battle.setB1Name(battle.getB1Name() + "+1");
                    break;
                case "B2":
                    battle.setB2Name(battle.getB2Name() + "+1");
                    break;
            }
        }

        //前台已做限制
//        if(battleService.hasNotCompletedBattlesByMemberId(accepter))
//            return AjaxResult.failure(ResultCode.BATTLE_HAS_NOT_COMPLETED, battleService.selectBattlesFromNow());

        int result = 0;
        try {
            locks[battle.getBattleId() % locks.length].lock();
            //在获锁的过程中，活动有可能被取消
            tempbattle = battleService.selectByPrimaryKey(battle.getBattleId());
            if (null == battle)
                return AjaxResult.failure(ResultCode.BATTLE_HAS_CANCELLED, battleService.selectBattlesFromNow());

            if (tempbattle.getBattleState() == 2)
                return AjaxResult.failure(ResultCode.BATTLE_HAS_ENOUGH_MEMBER, battleService.selectBattlesFromNow());

            if (battle.getA1() != null) {
                if (tempbattle.getA1() != null)
                    return AjaxResult.failure(ResultCode.BATTLE_POSITION_HAS_OCCUPIED, battleService.selectBattlesFromNow());
                else if (tempbattle.getA2() != null && tempbattle.getB1() != null && tempbattle.getB2() != null)
                    battle.setBattleState(2); //人齐了，约战自动成功
            } else if (battle.getA2() != null) {
                if (tempbattle.getA2() != null)
                    return AjaxResult.failure(ResultCode.BATTLE_POSITION_HAS_OCCUPIED, battleService.selectBattlesFromNow());
                else if (tempbattle.getA1() != null && tempbattle.getB1() != null && tempbattle.getB2() != null)
                    battle.setBattleState(2); //人齐了，约战自动成功
            } else if (battle.getB1() != null) {
                if (tempbattle.getB1() != null)
                    return AjaxResult.failure(ResultCode.BATTLE_POSITION_HAS_OCCUPIED, battleService.selectBattlesFromNow());
                else if (tempbattle.getA1() != null && tempbattle.getA2() != null && tempbattle.getB2() != null)
                    battle.setBattleState(2); //人齐了，约战自动成功
            } else if (battle.getB2() != null) {
                if (tempbattle.getB2() != null)
                    return AjaxResult.failure(ResultCode.BATTLE_POSITION_HAS_OCCUPIED, battleService.selectBattlesFromNow());
                else if (tempbattle.getA1() != null && tempbattle.getA2() != null && tempbattle.getB1() != null)
                    battle.setBattleState(2); //人齐了，约战自动成功
            }

            result = battleService.accept(battle, accepter);
        } finally {
            locks[battle.getBattleId() % locks.length].unlock();
        }

        return result == 1 ? AjaxResult.success(battleService.selectBattlesFromNow()) : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR, battleService.selectBattlesFromNow());
    }

    /**
     * 应战人员退出（管理员操作）
     *
     * @return
     */
    @PostMapping("/quit/{battleId}/{position}/{quiter}/{battlePoint}")
    @ResponseBody
    public AjaxResult<List<SunBattle>> quit(@PathVariable("battleId") Integer battleId, @PathVariable("position") String position, @PathVariable("quiter") Integer quiter, @PathVariable("battlePoint") Integer battlePoint) {
        int result = battleService.quit(battleId, position, quiter, battlePoint);
        return result == 1 ? AjaxResult.success(battleService.selectBattlesFromNow()) : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR, battleService.selectBattlesFromNow());
    }

}
