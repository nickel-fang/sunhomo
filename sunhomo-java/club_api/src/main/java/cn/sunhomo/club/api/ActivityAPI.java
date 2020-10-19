package cn.sunhomo.club.api;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunMember;
import cn.sunhomo.club.service.ISunActivityService;
import cn.sunhomo.core.AjaxResult;
import cn.sunhomo.core.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/club/activity")
public class ActivityAPI {
    @Autowired
    private ISunActivityService activityService;


    /**
     * 获取活动列表
     *
     * @param activity
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public List<SunActivity> list(@RequestBody SunActivity activity) {
        return activityService.selectActivities(activity);
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
        int result = activityService.quit(activityId, isMaster, member);
        return result == 1 ? AjaxResult.success(activityService.selectActivity(activityId)) : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR, activityService.selectActivity(activityId));
    }
}
