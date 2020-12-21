package cn.sunhomo.sys.service;

import cn.sunhomo.sys.domain.SysRole;

import java.util.List;
import java.util.Set;

/**
 * @author: Nickel Fang
 * @date: 2020/12/21 16:06
 */
public interface ISysRoleService {
    SysRole selectByPrimaryKey(Integer roleId);

    List<SysRole> selectSysRoles();

    Set<String> selectRoleKeysByUserId(Integer userId);
}
