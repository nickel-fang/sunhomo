package com.sunhomo.api.enroll;

import com.sunhomo.common.core.domain.AjaxResult;
import com.sunhomo.enroll.domain.SunActivity;
import com.sunhomo.enroll.domain.SunMember;
import com.sunhomo.enroll.service.ISunActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.ReferenceType;
import java.util.List;

@Controller
@RequestMapping("/api/enroll/activity")
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
    public List<SunActivity> list(SunActivity activity) {
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
    public AjaxResult enroll(@PathVariable("activityId") Integer activityId, @RequestBody SunMember member) {
        int success = activityService.enroll(activityId, member);
        if (success == 1) return AjaxResult.success("1", activityService.selectActivity(activityId));
        else return AjaxResult.success("0", activityService.selectActivity(activityId));
    }
}
