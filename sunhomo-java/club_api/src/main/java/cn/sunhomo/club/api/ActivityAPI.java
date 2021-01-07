package cn.sunhomo.club.api;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunDivision;
import cn.sunhomo.club.domain.SunMember;
import cn.sunhomo.club.service.ISunActivityService;
import cn.sunhomo.club.service.ISunDivisionService;
import cn.sunhomo.core.AjaxResult;
import cn.sunhomo.core.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/club/activity")
public class ActivityAPI {
    @Autowired
    private ISunActivityService activityService;

    @Autowired
    private ISunDivisionService divisionService;

    private static final Lock lock = new ReentrantLock();


    /**
     * 获取活动列表
     *
     * @param activity
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public List<SunActivity> list(@RequestBody SunActivity activity) {
        List<SunActivity> list = activityService.selectActivities(activity);
        return list;
    }

    /**
     * 获取用户当前主报名的普能打球活动, 所有状态为1的
     *
     * @param memberId
     * @return
     */
    @PostMapping("/getActivitiesForBattle")
    @ResponseBody
    public List<SunActivity> getActivitiesForBattle(@RequestBody Integer memberId) {
        List<SunActivity> list = activityService.getActivitiesForBattle(memberId);
        return list;
    }

    /**
     * 获取单个活动详情
     *
     * @param activityId
     * @return
     */
    @PostMapping("/getActivity")
    @ResponseBody
    public SunActivity getActivity(@RequestBody Integer activityId) {
        SunActivity activity = activityService.selectActivity(activityId);
        activity.setContent();

        //比赛类型的活动，同时抽签时间已到
        if (activity.getActivityType() == 2) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime draw = LocalDateTime.parse(activity.getDrawTime(), dateTimeFormatter);
            if (LocalDateTime.now().isAfter(draw))
                activity.setCanDraw(true);
        }
        return activity;
    }

    /**
     * 获取单个比赛活动分组情况
     *
     * @param activityId
     * @return
     */
    @PostMapping("/getDivisions")
    @ResponseBody
    public List<SunDivision> getDivisions(@RequestBody Integer activityId) {
        return divisionService.selectDivisions(activityId);
    }

    /**
     * 会员报名活动
     *
     * @param member
     * @return AjaxResult(SunActivity)
     */
    @PostMapping("/enroll/{activityId}")
    @ResponseBody
    public AjaxResult<SunActivity> enroll(@PathVariable("activityId") Integer activityId, @RequestBody SunMember member) {
        SunActivity activity = activityService.selectActivity(activityId);
        //活动已开始，不允许报名
        if (LocalDateTime.now().isAfter(LocalDateTime.parse(activity.getActivityDate() + " " + activity.getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))) {
            return AjaxResult.failure(ResultCode.ACTIVITY_HAS_STARTED, activityService.selectActivity(activityId));
        }
        //比赛活动不能带挂报名
        if (activity.getActivityType() == 2 && activityService.selectCount(activityId, member.getMemberId()).size() >= 1) {
            return AjaxResult.failure(ResultCode.ATTACH_IS_NOT_ALLOWED, activityService.selectActivity(activityId));
        }
        int result = activityService.enroll(activityId, member);
        return result == 1 ? AjaxResult.success(activityService.selectActivity(activityId)) : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR, activityService.selectActivity(activityId));
    }

    /**
     * 取消报名
     *
     * @param activityId
     * @param isMaster
     * @param isAdmin
     * @param memberId
     * @return
     */
    @PostMapping("/quit/{activityId}/{isMaster}/{isAdmin}")
    @ResponseBody
    public AjaxResult<SunActivity> quit(@PathVariable("activityId") Integer activityId, @PathVariable("isMaster") Byte isMaster, @PathVariable("isAdmin") Byte isAdmin, @RequestBody Integer memberId) {
        SunActivity activity = activityService.selectActivity(activityId);
        int result;
        //管理者可直接取消
        if (isAdmin == 1) {
            result = activityService.quit(activityId, isMaster, memberId);
            return result == 1 ? AjaxResult.success(activityService.selectActivity(activityId)) : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR, activityService.selectActivity(activityId));
        }

        //根据活动取消报名截止时间来判断是否可进行取消操作
        //截止时间之前，可取消
        if (LocalDateTime.now().isBefore(LocalDateTime.parse(activity.getDeadline(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))) {
            result = activityService.quit(activityId, isMaster, memberId);
            return result == 1 ? AjaxResult.success(activityService.selectActivity(activityId)) : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR, activityService.selectActivity(activityId));
        }

        //截止时间之后 and 报名未满，不允许取消
        if (activity.getMembers().size() <= activity.getNumbers()) {
            return AjaxResult.failure(ResultCode.BUSINESS_AFTER_APPOINTED_TIME, activityService.selectActivity(activityId));
        }

        //截止时间之后 and 有替补，活动当天联系管理员取消
        if (LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals(activity.getActivityDate())) {
            return AjaxResult.failure(ResultCode.CONTACT_LEADER_FOR_CANCEL, activityService.selectActivity(activityId));
        }

        //有替补，可取消，此时要控制并发问题，加锁
        try {
            lock.lock();
            if (activityService.selectActivity(activityId).getMembers().size() <= activity.getNumbers()) {
                //有替补可取消。但在等锁的过程中，被别的人取消了，所以此时重新获取下活动报名数
                return AjaxResult.failure(ResultCode.BUSINESS_AFTER_APPOINTED_TIME, activityService.selectActivity(activityId));
            }
            result = activityService.quit(activityId, isMaster, memberId);
            return result == 1 ? AjaxResult.success(activityService.selectActivity(activityId)) : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR, activityService.selectActivity(activityId));
        } finally {
            lock.unlock();
        }
    }

    /**
     * 比赛活动抽签
     *
     * @param member
     * @return AjaxResult(SunActivity)
     */
    @PostMapping("/draw/{activityId}")
    @ResponseBody
    public AjaxResult<SunActivity> draw(@PathVariable("activityId") Integer activityId, @RequestBody SunMember member) {
        SunActivity activity = activityService.selectActivity(activityId);
        //抽签时间未到
        if (LocalDateTime.now().isBefore(LocalDateTime.parse(activity.getDrawTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))) {
            return AjaxResult.failure(ResultCode.DRAW_IS_NOT_ALLOWED_FOR_TIME);
        }

        //是否报过名
        boolean isEnrolled = false;
        for (SunMember sunMember : activity.getMembers()) {
            if (sunMember.getMemberId().intValue() == member.getMemberId().intValue()) {
                isEnrolled = true;
                break;
            }
        }
        if (!isEnrolled) return AjaxResult.failure(ResultCode.DRAW_IS_NOT_ALLOWED_FOR_OTHER_PERSON);

        //队长无须抽签；或已抽过签
        List<SunDivision> divisions = divisionService.selectDivisions(activityId);
        if (divisions.size() != activity.getDivisions()) {
            return AjaxResult.failure(ResultCode.DRAW_DIVISION_IS_NOT_CONFIGURED);
        }
        for (SunDivision division : divisions) {
            if (division.getLeader().intValue() == member.getMemberId().intValue()) {
                return AjaxResult.failure(ResultCode.DRAW_IS_NOT_NEEDED_FOR_LEADER);
            }
            for (SunMember sunMember : division.getMembers()) {
                if (sunMember.getMemberId().intValue() == member.getMemberId().intValue()) {
                    return AjaxResult.failure(ResultCode.DRAW_IS_DONE);
                }
            }
        }

        //执行抽签操作
        try {
            //每组人数
            int number = activity.getNumbers() / activity.getDivisions();
            lock.lock();
            List<SunDivision> underFillDivision = divisionService.selectDivisions(activityId).stream().filter(d -> d.getMembers().size() < number - 1).collect(Collectors.toList());
            if (underFillDivision != null && underFillDivision.size() > 0) {
                SunDivision division = underFillDivision.get(new Random().nextInt(underFillDivision.size()));
                divisionService.draw(member.getMemberId(), division.getDivisionId());
            }
        } finally {
            lock.unlock();
        }
        return AjaxResult.success();
    }
}
