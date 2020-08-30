package com.sunhomo.web.controller.enroll;

import com.sunhomo.common.core.controller.BaseController;
import com.sunhomo.enroll.domain.SunActivity;
import com.sunhomo.enroll.service.ISunActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/enroll/activity")
public class ActivityController extends BaseController {
    private String prefix = "enroll/activity";
    @Autowired
    private ISunActivityService activityService;

    @GetMapping("/list")
    public List<SunActivity> list() {
        List<SunActivity> activities = activityService.selectActivities((byte) 1);
        return activities;
    }
}
