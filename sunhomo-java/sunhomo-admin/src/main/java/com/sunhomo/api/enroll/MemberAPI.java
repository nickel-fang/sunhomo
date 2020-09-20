package com.sunhomo.api.enroll;

import com.alibaba.fastjson.JSON;
import com.sunhomo.common.core.domain.AjaxResult;
import com.sunhomo.common.utils.StringUtils;
import com.sunhomo.enroll.domain.SunMember;
import com.sunhomo.enroll.service.ISunMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Controller
@RequestMapping("/api/enroll/member")
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
            return AjaxResult.error("wx.login失败！");
        }
        SunMember member = memberService.selectMember(weiChatObject.getOpenid());
        if (member == null) { //数据库无该会员
            return AjaxResult.success("-1", weiChatObject.getOpenid());
        } else if (StringUtils.isEmpty(member.getMemberName())) { //数据库中有此会员，但信息不全
            return AjaxResult.success("0", member);
        } else { //数据库中有此会员，信息全
            return AjaxResult.success("1", member);
        }
    }

    @PostMapping("/insertMember")
    @ResponseBody
    public AjaxResult insertMember(@RequestBody SunMember member) {

        member.setSignDate(new Date());
        memberService.insertMember(member);
        return AjaxResult.success(memberService.selectMember(member.getOpenid()));
    }

}
