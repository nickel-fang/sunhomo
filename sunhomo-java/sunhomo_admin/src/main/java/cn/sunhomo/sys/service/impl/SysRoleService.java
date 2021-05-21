package cn.sunhomo.sys.service.impl;

import cn.sunhomo.sys.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.sunhomo.sys.mapper.SysRoleDao;
import cn.sunhomo.sys.domain.SysRole;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: Nickel Fang
 * @date: 2020/12/21 16:01
 */
@Service
public class SysRoleService implements ISysRoleService {

    @Autowired
    private SysRoleDao sysRoleMapper;

    @Override
    public SysRole selectByPrimaryKey(Integer roleId) {
        return sysRoleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public List<SysRole> selectSysRoles() {
        return null;
    }

    @Override
    public Set<String> selectRoleKeysByUserId(Integer userId) {
        Set<String> roleKeys = new HashSet<>();
        roleKeys.addAll(sysRoleMapper.selectRoleKeysByUserId(userId));
        return roleKeys;
    }
}
