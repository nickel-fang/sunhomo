package cn.sunhomo.club.controller;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunDivision;
import cn.sunhomo.club.service.ISunDivisionService;
import cn.sunhomo.controller.BaseController;
import cn.sunhomo.core.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/club/division")
public class DivisionController extends BaseController {
    private String prefix = "club/division";

    @Autowired
    private ISunDivisionService divisionService;


    @GetMapping("/activity/{id}")
    public String division(@PathVariable("id") Integer activityId, ModelMap mmap) {
        List<SunDivision> divisions = divisionService.selectDivisions(activityId);

        mmap.put("divisions", divisions);
        return prefix + "/division";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(@RequestParam(value = "activityId", required = true) Integer activityId) {
        List<SunDivision> divisions = divisionService.selectDivisions(activityId);
        return getDataTable(divisions);
    }
}
