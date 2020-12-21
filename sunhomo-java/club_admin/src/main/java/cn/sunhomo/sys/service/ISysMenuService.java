package cn.sunhomo.sys.service;

import cn.sunhomo.sys.domain.SysMenu;

import java.util.List;
import java.util.Set;

/**
 * @author: Nickel Fang
 * @date: 2020/12/21 16:04
 */
public interface ISysMenuService {
    SysMenu selectByPrimaryKey(Integer menuId);

    List<SysMenu> selectSysMenus();

    Set<String> selectMenuPermsByUserId(Integer userId);
}
