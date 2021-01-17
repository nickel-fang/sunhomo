package cn.sunhomo.club.api;

import cn.sunhomo.club.domain.*;
import cn.sunhomo.core.AjaxResult;
import cn.sunhomo.util.StringUtils;
import com.alibaba.fastjson.JSON;
import cn.sunhomo.club.service.ISunMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/club/member")
@Slf4j
public class MemberAPI {

    @Value("${weiChat.miniProgram.appId}")
    private String appId;
    @Value("${weiChat.miniProgram.appSecret}")
    private String appSecret;

    @Autowired
    private ISunMemberService memberService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/getOpenId")
    @ResponseBody
    public AjaxResult code2Session(@RequestBody String code) {
        String object = restTemplate.getForObject("https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code", String.class);
        WeiChatObject weiChatObject = JSON.parseObject(object, WeiChatObject.class);
        if (StringUtils.isEmpty(weiChatObject.getOpenid())) {
            log.error("wx.login失败");
            return new AjaxResult(500, "wx.login失败！", null);
        }
        SunMember member = memberService.selectMember(weiChatObject.getOpenid());
        if (member == null) { //数据库无该会员
            return new AjaxResult<String>(-1, "数据库无该会员！", weiChatObject.getOpenid());
        } else if (StringUtils.isEmpty(member.getMemberName())) { //
            return new AjaxResult<SunMember>(0, "数据库中有此会员，但信息不全！", member);
        } else { //数据库中有此会员，信息全
            return new AjaxResult<SunMember>(1, "数据库中有此会员，信息全！", member);
        }
    }

    @PostMapping("/insertMember")
    @ResponseBody
    public AjaxResult<SunMember> insertMember(@RequestBody SunMember member) {

        member.setSignDate(new Date());
        memberService.insertMember(member);
        return new AjaxResult<SunMember>(1, "会员添加成功", memberService.selectMember(member.getOpenid()));
    }

    @PostMapping("/myPointRecords")
    @ResponseBody
    public List<SunPointRecord> myPointRecords(@RequestBody Integer memberId) {
        List<SunPointRecord> pointRecords = memberService.getPointRecordsByMemberID(memberId);
        return pointRecords;
    }

    @PostMapping("/yearPointRecords")
    @ResponseBody
    public List<SunPointRecord> yearPointRecords(@RequestBody String openid) {
        List<SunPointRecord> pointRecords = memberService.getYearPointRecordsByOpenID(openid);
        return pointRecords;
    }

    @PostMapping("/myActivities")
    @ResponseBody
    public List<SunActivity> myActivities(@RequestBody Integer memberId) {
        List<SunActivity> activities = memberService.getActivitiesByMemberID(memberId);
        return activities;
    }

    @PostMapping("/myBattles")
    @ResponseBody
    public List<SunBattle> myBattles(@RequestBody Integer memberId) {
        List<SunBattle> battles = memberService.getBattlesByMemberID(memberId);
        return battles;
    }

    @PostMapping("/myRedeem")
    @ResponseBody
    public List<SunGoodTransaction> myRedeem(@RequestBody Integer memberId) {
        List<SunGoodTransaction> goodTransactions = memberService.getGoodTransactionsByMemberID(memberId);
        return goodTransactions;
    }

    @PostMapping("/myYearPointRange")
    @ResponseBody
    public List<SunMember> myYearPointRange(@RequestBody SunMember member) {
        List<SunMember> members = memberService.getTop10YearPointByMember(member);
        return members;
    }

}
