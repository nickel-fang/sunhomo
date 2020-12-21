package cn.sunhomo.sys.service.impl;

import cn.sunhomo.sys.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.sunhomo.sys.mapper.SysMenuDao;
import cn.sunhomo.sys.domain.SysMenu;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return null;
    }

    @Override
    public Set<String> selectMenuPermsByUserId(Integer userId) {
        Set<String> perms = new HashSet<>();
        perms.addAll(sysMenuMapper.selectMenuPermsByUserId(userId));
        return perms;
    }

}
