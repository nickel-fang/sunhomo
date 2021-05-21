package cn.sunhomo.sys.mapper;

import cn.sunhomo.sys.domain.SysRole;

import java.util.Collection;
import java.util.List;

/**
 * @author: Nickel Fang
 * @date: 2020/12/21 16:01
 */
public interface SysRoleDao {
    int deleteByPrimaryKey(Integer roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<String> selectRoleKeysByUserId(Integer userId);
}