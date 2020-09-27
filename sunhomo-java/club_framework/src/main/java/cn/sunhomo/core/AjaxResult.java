package cn.sunhomo.core;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AjaxResult<T> {
    //1:成功 0：失败
    private int code;
    private String msg;
    private T object;
}
