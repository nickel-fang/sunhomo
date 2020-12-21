package cn.sunhomo.sys.mapper;

import cn.sunhomo.sys.domain.SysMenu;

import java.util.Collection;
import java.util.List;

/**
 * @author: Nickel Fang
 * @date: 2020/12/21 16:01
 */
public interface SysMenuDao {
    int deleteByPrimaryKey(Integer menuId);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Integer menuId);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    List<String> selectMenuPermsByUserId(Integer userId);
}