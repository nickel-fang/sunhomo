package cn.sunhomo.sys.service.impl;

import cn.sunhomo.sys.service.ISysUserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.sunhomo.sys.mapper.SysUserDao;
import cn.sunhomo.sys.domain.SysUser;

import java.util.List;

/**
 * @author: Nickel Fang
 * @date: 2020/12/21 16:01
 */
@Service
public class SysUserService implements ISysUserService {

    @Autowired
    private SysUserDao sysUserMapper;

    public SysUser selectByPrimaryKey(Integer userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<SysUser> selectSysUsers() {
        return null;
    }

    @Override
    public SysUser login(String username, String password) throws Exception {
        SysUser user = sysUserMapper.selectUserByLoginName(username);
        if (user == null || user.getPassword().equals(encryptPassword(username, password, user.getSalt()))) {
            throw new Exception("login fail");
        }
        return user;
    }

    public String encryptPassword(String username, String password, String salt) {
        return new Md5Hash(username + password + salt).toHex();
    }
}
