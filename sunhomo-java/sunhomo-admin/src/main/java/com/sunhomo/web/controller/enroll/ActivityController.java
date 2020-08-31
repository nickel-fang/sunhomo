package com.sunhomo.web.controller.enroll;

import com.sunhomo.common.annotation.Log;
import com.sunhomo.common.core.controller.BaseController;
import com.sunhomo.common.core.domain.AjaxResult;
import com.sunhomo.common.core.page.TableDataInfo;
import com.sunhomo.common.enums.BusinessType;
import com.sunhomo.enroll.domain.SunActivity;
import com.sunhomo.enroll.service.ISunActivityService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/enroll/activity")
public class ActivityController extends BaseController {
    private String prefix = "enroll/activity";
    @Autowired
    private ISunActivityService activityService;

    @GetMapping()
    public String list() {
        return prefix + "/activity";
    }

    @PostMapping("/list")
    @RequiresPermissions("enroll:activity:list")
    @ResponseBody
    public TableDataInfo list(SunActivity activity) {
        startPage();
        List<SunActivity> activities = activityService.selectActivities(activity);
        return getDataTable(activities);
    }

    @GetMapping("/add")
    public String add(ModelMap mmap) {
        return prefix + "/add";
    }

    @RequiresPermissions("enroll:activity:add")
    @Log(title = "活动管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SunActivity activity) {
        return toAjax(activityService.insertActivity(activity));
    }

    @RequiresPermissions("enroll:activity:remove")
    @Log(title = "活动管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(activityService.deleteActivityById(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer activityId, ModelMap mmap) {
        mmap.put("activity", activityService.selectActivity(activityId));
        return prefix + "/edit";
    }

    @RequiresPermissions("enroll:activity:edit")
    @Log(title = "活动管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SunActivity activity) {
        return toAjax(activityService.updateActivity(activity));
    }
}
