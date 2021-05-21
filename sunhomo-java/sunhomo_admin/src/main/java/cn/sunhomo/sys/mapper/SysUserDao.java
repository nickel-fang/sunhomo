package cn.sunhomo.sys.mapper;

import cn.sunhomo.sys.domain.SysUser;

/**
* @author: Nickel Fang
* @date: 2020/12/21 16:01
*/
public interface SysUserDao {
    int deleteByPrimaryKey(Integer userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser selectUserByLoginName(String username);
}