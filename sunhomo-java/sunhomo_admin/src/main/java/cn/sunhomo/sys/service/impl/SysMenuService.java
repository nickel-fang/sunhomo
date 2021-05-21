package cn.sunhomo.sys.service.impl;

import cn.sunhomo.sys.domain.SysUser;
import cn.sunhomo.sys.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.sunhomo.sys.mapper.SysMenuDao;
import cn.sunhomo.sys.domain.SysMenu;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: Nickel Fang
 * @date: 2020/12/21 16:01
 */
@Service
public class SysMenuService implements ISysMenuService {

    @Autowired
    private SysMenuDao sysMenuMapper;

    @Override
    public SysMenu selectByPrimaryKey(Integer menuId) {
        return sysMenuMapper.selectByPrimaryKey(menuId);
    }

    @Override
    public List<SysMenu> selectSysMenus() {
        return sysMenuMapper.selectMenus();
    }

    @Override
    public Set<String> selectMenuPermsByUserId(Integer userId) {
        Set<String> perms = new HashSet<>();
        perms.addAll(sysMenuMapper.selectMenuPermsByUserId(userId));
        return perms;
    }

    @Override
    public List<SysMenu> selectSysMenusByUser(SysUser user) {
        List<SysMenu> menus = new LinkedList<>();
        if (user.isAdmin()) {
            menus = sysMenuMapper.selectMenus();
        } else {
            menus = sysMenuMapper.selectMenusByUserId(user.getUserId());
        }
        return getTreeOfSysMenus(0, menus);
    }

    private List<SysMenu> getTreeOfSysMenus(int root, List<SysMenu> menus) {
        return menus.stream().filter(m -> m.getParentId() == root).map(m -> {
            m.setChildren(getChildrens(m, menus));
            return m;
        }).collect(Collectors.toList());
    }

    private List<SysMenu> getChildrens(SysMenu root, List<SysMenu> menus) {
        return menus.stream().filter(m -> m.getParentId().intValue() == root.getMenuId().intValue()).peek(m -> {
            m.setChildren(getChildrens(m, menus));
        }).collect(Collectors.toList());
    }

}
