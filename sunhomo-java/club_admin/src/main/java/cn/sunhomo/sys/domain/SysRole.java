package cn.sunhomo.sys.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
* @author: Nickel Fang
* @date: 2020/12/21 16:01
*/
@Data
@AllArgsConstructor
public class SysRole {
    /**
    * 角色ID
    */
    private Integer roleId;

    /**
    * 角色名称
    */
    private String roleName;

    /**
    * 角色权限字符串
    */
    private String roleKey;

    /**
    * 显示顺序
    */
    private Integer roleSort;

    /**
    * 角色状态（0正常 1停用）
    */
    private String status;

    /**
    * 备注
    */
    private String remark;
}