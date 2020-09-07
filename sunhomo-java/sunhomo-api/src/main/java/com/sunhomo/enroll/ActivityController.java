package com.sunhomo.enroll;

import com.sunhomo.enroll.domain.SunActivity;
import com.sunhomo.enroll.service.ISunActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author: Nickel Fang
 * @date: 2020/9/7 13:34
 */
@Controller
@RequestMapping("/enroll/activity")
public class ActivityController {
    @Autowired
    private ISunActivityService activityService;

    @PostMapping("/list")
    @ResponseBody
    public List<SunActivity> list(SunActivity activity) {
        return activityService.selectActivities(activity);
    }
}
