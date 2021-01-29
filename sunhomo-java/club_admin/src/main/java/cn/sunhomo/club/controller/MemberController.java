package cn.sunhomo.club.controller;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunMember;
import cn.sunhomo.club.service.ISunMemberService;
import cn.sunhomo.controller.BaseController;
import cn.sunhomo.core.AjaxResult;
import cn.sunhomo.core.ResultCode;
import cn.sunhomo.core.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Nickel Fang
 * @date: 2021/1/29 13:31
 */
@Controller
@RequestMapping("/club/member")
public class MemberController extends BaseController {
    private String prefix = "club/member";
    @Autowired
    private ISunMemberService memberService;

    @GetMapping()
    public String list() {
        return prefix + "/member";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SunMember member) {
        startPage();
        List<SunMember> members = memberService.selectMembers(member);
        return getDataTable(members);
    }

    @PostMapping("/editBlack")
    @ResponseBody
    public AjaxResult editBlack(@RequestBody SunMember member) {
        int row = memberService.updateMember(member);
        return row > 0 ? AjaxResult.success() : AjaxResult.failure(ResultCode.SYSTEM_INNER_ERROR);
    }
}
