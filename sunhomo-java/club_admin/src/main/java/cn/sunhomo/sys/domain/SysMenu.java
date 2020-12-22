package cn.sunhomo.sys.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
* @author: Nickel Fang
* @date: 2020/12/21 16:01
*/
@Data
public class SysMenu {
    /**
    * 菜单ID
    */
    private Integer menuId;

    /**
    * 菜单名称
    */
    private String menuName;

    /**
    * 父菜单ID
    */
    private Integer parentId;

    /**
    * 显示顺序
    */
    private Integer orderNum;

    /**
    * 请求地址
    */
    private String url;

    /**
    * 打开方式（menuItem页签 menuBlank新窗口）
    */
    private String target;

    /**
    * 菜单类型（M目录 C菜单 F按钮）
    */
    private String menuType;

    /**
    * 菜单状态（0显示 1隐藏）
    */
    private String visible;

    /**
    * 权限标识
    */
    private String perms;

    /**
    * 菜单图标
    */
    private String icon;

    /**
    * 备注
    */
    private String remark;

    private List<SysMenu> children = new ArrayList<SysMenu>();
}