package cn.sunhomo.club.api;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunAd;
import cn.sunhomo.club.service.ISunAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/club/ad")
public class AdAPI {
    @Autowired
    private ISunAdService adService;

    @PostMapping("/list")
    @ResponseBody
    public List<SunAd> list() {
        List<SunAd> list = adService.getAds();
        return list;
    }
}
