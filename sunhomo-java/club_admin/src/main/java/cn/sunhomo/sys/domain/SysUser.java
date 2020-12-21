package cn.sunhomo.sys.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Nickel Fang
 * @date: 2020/12/21 16:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 登录账号
     */
    private String loginName;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐加密
     */
    private String salt;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    public boolean isAdmin() {
        return userId != null && userId == 1;
    }
}