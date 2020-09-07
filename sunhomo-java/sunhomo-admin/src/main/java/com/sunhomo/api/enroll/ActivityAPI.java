package com.sunhomo.api.enroll;

import com.sunhomo.enroll.domain.SunActivity;
import com.sunhomo.enroll.service.ISunActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/enroll/activity")
public class ActivityAPI {
    @Autowired
    private ISunActivityService activityService;


    @PostMapping("/list")
    @ResponseBody
    public List<SunActivity> list(SunActivity activity) {
        return activityService.selectActivities(activity);
    }

}
