package cn.sunhomo.club.api;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunMember;
import cn.sunhomo.club.service.ISunActivityService;
import cn.sunhomo.core.AjaxResult;
import cn.sunhomo.core.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Controller
@RequestMapping("/club/activity")
public class ActivityAPI {
    @Autowired
    private ISunActivityService activityService;

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
     * 获取单个活动详情
     *
     * @param activityId
     * @return
     */
    @PostMapping("/getActivity")
    @ResponseBody
    public SunActivity getActivity(@RequestBody Integer activityId) {
        return activityService.selectActivity(activityId);
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
        int result = activityService.enroll(activityId, member);
        return result == 1 ? AjaxResult.success(activityService.selectActivity(activityId)) : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR, activityService.selectActivity(activityId));
    }

    /**
     * 取消报名
     *
     * @param activityId
     * @param member
     * @return
     */
    @PostMapping("/quit/{activityId}/{isMaster}")
    @ResponseBody
    public AjaxResult<SunActivity> quit(@PathVariable("activityId") Integer activityId, @PathVariable("isMaster") Byte isMaster, @RequestBody SunMember member) {
        SunActivity activity = activityService.selectActivity(activityId);
        int result;
        //根据活动取消报名截止时间来判断是否可进行取消操作
        //截止时间之前，可取消
        if (LocalDateTime.now().isBefore(LocalDateTime.parse(activity.getDeadline(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))) {
            result = activityService.quit(activityId, isMaster, member);
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
            result = activityService.quit(activityId, isMaster, member);
            return result == 1 ? AjaxResult.success(activityService.selectActivity(activityId)) : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR, activityService.selectActivity(activityId));
        } finally {
            lock.unlock();
        }
    }
}
