package cn.sunhomo.club.controller;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunMember;
import cn.sunhomo.club.domain.SunPointRecord;
import cn.sunhomo.club.service.ISunMemberService;
import cn.sunhomo.club.service.ISunPointService;
import cn.sunhomo.controller.BaseController;
import cn.sunhomo.core.AjaxResult;
import cn.sunhomo.core.ResultCode;
import cn.sunhomo.core.TableDataInfo;
import cn.sunhomo.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    @Autowired
    private ISunPointService pointService;

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

    @GetMapping("/recommend/{from}/{to}/{recordDate}")
    @ResponseBody
    public void recommend(@PathVariable("from") Integer from, @PathVariable("to") Integer to, @PathVariable("recordDate") String date) throws ParseException {

        SunMember fromMember = memberService.selectMember(from);
        SunMember toMember = memberService.selectMember(to);

        fromMember.addAllPoint(5); //推荐人奖励
        toMember.addAllPoint(7); //被推荐人奖励+新人首次报名

        List<SunPointRecord> insertPointRecords = new ArrayList<SunPointRecord>();
        Date recordDate = DateUtils.parseDate(date, "yyyyMMdd");
        insertPointRecords.add(new SunPointRecord(null, from, (byte) 5, "系统奖励：拉新人奖励", 5, recordDate));
        insertPointRecords.add(new SunPointRecord(null, to, (byte) 5, "系统奖励：小程序首次报名", 2, recordDate));
        insertPointRecords.add(new SunPointRecord(null, to, (byte) 5, "系统奖励：新人奖励", 5, recordDate));

        pointService.updateMembersPoint(Arrays.asList(fromMember, toMember), insertPointRecords);
    }
}
