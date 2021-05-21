package cn.sunhomo.shiro.realm;

import cn.sunhomo.shiro.ShiroUtils;
import cn.sunhomo.sys.domain.SysUser;
import cn.sunhomo.sys.service.ISysMenuService;
import cn.sunhomo.sys.service.ISysRoleService;
import cn.sunhomo.sys.service.ISysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: Nickel Fang
 * @date: 2020/12/21 15:43
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysMenuService menuService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser user = ShiroUtils.getSysUser();
        Set<String> roles = new HashSet<>();
        Set<String> menus = new HashSet<>();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (user.isAdmin()) {
            info.addRole("admin");
            info.addStringPermission("*:*:*");
        } else {
            roles = roleService.selectRoleKeysByUserId(user.getUserId());
            menus = menuService.selectMenuPermsByUserId(user.getUserId());
            info.setRoles(roles);
            info.setStringPermissions(menus);
        }

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = "";
        if (token.getPassword() != null) {
            password = new String(token.getPassword());
        }
        SysUser user = null;
        try {
            user = userService.login(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticationException(e.getMessage(), e);
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());

        return info;
    }
}
