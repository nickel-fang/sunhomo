package cn.sunhomo.sys.service;

import cn.sunhomo.sys.domain.SysUser;

import java.util.List;

/**
 * @author: Nickel Fang
 * @date: 2020/12/21 16:06
 */
public interface ISysUserService {
    SysUser selectByPrimaryKey(Integer userId);

    List<SysUser> selectSysUsers();

    SysUser login(String username, String password) throws Exception;
}
