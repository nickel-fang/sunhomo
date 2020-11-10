package cn.sunhomo.club.controller;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunDivision;
import cn.sunhomo.club.service.ISunActivityService;
import cn.sunhomo.club.service.ISunDivisionService;
import cn.sunhomo.controller.BaseController;
import cn.sunhomo.core.AjaxResult;
import cn.sunhomo.core.ResultCode;
import cn.sunhomo.core.TableDataInfo;
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
//    @RequiresPermissionsons("club:activity:list")
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

    //    @RequiresPermissions("club:activity:add")
//    @Log(title = "活动管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SunActivity activity) {
        int result = activityService.insertActivity(activity);
        return result == 1 ? AjaxResult.success() : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    //    @RequiresPermissions("club:activity:remove")
//    @Log(title = "活动管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            activityService.deleteActivityById(ids);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer activityId, ModelMap mmap) {
        mmap.put("activity", activityService.selectActivity(activityId));
        return prefix + "/edit";
    }

    //    @RequiresPermissions("club:activity:edit")
//    @Log(title = "活动管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SunActivity activity) {
        int result = activityService.updateActivity(activity);
        return result == 1 ? AjaxResult.success() : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

}
