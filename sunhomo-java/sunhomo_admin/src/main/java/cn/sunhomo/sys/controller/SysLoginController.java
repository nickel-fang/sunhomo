package cn.sunhomo.sys.controller;

import cn.sunhomo.controller.BaseController;
import cn.sunhomo.core.AjaxResult;
import cn.sunhomo.core.ResultCode;
import cn.sunhomo.shiro.ShiroUtils;
import cn.sunhomo.sys.domain.SysMenu;
import cn.sunhomo.sys.domain.SysUser;
import cn.sunhomo.sys.service.ISysMenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: Nickel Fang
 * @date: 2020/12/22 13:30
 */
@Controller
public class SysLoginController extends BaseController {
    @Autowired
    private ISysMenuService menuService;

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public AjaxResult login(String username, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return AjaxResult.success();
        } catch (AuthenticationException e) {
            return AjaxResult.failure(ResultCode.USER_LOGIN_ERROR);
        }
    }

    @GetMapping("/unauth")
    public String unauth() {
        return "error/unauth";
    }

    @GetMapping("/index")
    public String index(ModelMap mmap) {
        SysUser user = ShiroUtils.getSysUser();
        List<SysMenu> menus = menuService.selectSysMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        return "index";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main()
    {
        return "main";
    }
}
