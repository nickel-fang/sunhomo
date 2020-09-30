package cn.sunhomo.core;

import cn.sunhomo.util.StringUtils;
import lombok.Data;

/**
 * @author: Nickel Fang
 * @date: 2020/9/30 10:40
 */
@Data
public class PageRequest {
    private int pageNum;
    private int pageSize;
    //排序字段
    private String orderByColumn;
    //desc 或者 asc
    private String isAsc;

    public String getOrderBy(){
        if(StringUtils.isEmpty(orderByColumn)) return "";
        return StringUtils
    }
}
