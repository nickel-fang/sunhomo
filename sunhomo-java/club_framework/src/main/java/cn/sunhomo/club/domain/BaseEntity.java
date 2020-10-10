package cn.sunhomo.club.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Nickel Fang
 * @date: 2020/10/9 15:05
 */
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    //请求参数
    private Map<String, Object> params;

    public Map<String, Object> getParams() {
        return params == null ? new HashMap<String, Object>() : params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
