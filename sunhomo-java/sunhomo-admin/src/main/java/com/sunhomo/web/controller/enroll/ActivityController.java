package com.sunhomo.web.controller.enroll;

import com.sunhomo.common.core.controller.BaseController;
import com.sunhomo.common.core.page.TableDataInfo;
import com.sunhomo.enroll.domain.SunActivity;
import com.sunhomo.enroll.service.ISunActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/enroll/activity")
public class ActivityController extends BaseController {
    private String prefix = "enroll/activity";
    @Autowired
    private ISunActivityService activityService;

    @GetMapping()
    public String list() {
        return prefix+"/activity";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SunActivity activity){
        startPage();
        List<SunActivity> activities = activityService.selectActivities(activity);
        return  getDataTable(activities);
    }
}
