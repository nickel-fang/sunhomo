package com.sunhomo.web.controller.club;

import com.sunhomo.common.annotation.Log;
import com.sunhomo.common.core.controller.BaseController;
import com.sunhomo.common.core.domain.AjaxResult;
import com.sunhomo.common.core.page.TableDataInfo;
import com.sunhomo.common.enums.BusinessType;
import com.sunhomo.club.domain.SunActivity;
import com.sunhomo.club.service.ISunActivityService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/club/activity")
public class ActivityController extends BaseController {
    private String prefix = "club/activity";
    @Autowired
    private ISunActivityService activityService;

    @GetMapping()
    public String list() {
        return prefix + "/activity";
    }

    @PostMapping("/list")
    @RequiresPermissions("club:activity:list")
    @ResponseBody
    public TableDataInfo list(SunActivity activity) {
        startPage();
        List<SunActivity> activities = activityService.selectActivities(activity);
        return getDataTable(activities);
    }

    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    @RequiresPermissions("club:activity:add")
    @Log(title = "活动管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SunActivity activity) {
        return toAjax(activityService.insertActivity(activity));
    }

    @RequiresPermissions("club:activity:remove")
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

    @RequiresPermissions("club:activity:edit")
    @Log(title = "活动管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SunActivity activity) {
        return toAjax(activityService.updateActivity(activity));
    }
}
