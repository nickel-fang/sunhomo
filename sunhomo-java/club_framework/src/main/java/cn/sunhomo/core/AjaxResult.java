package cn.sunhomo.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AjaxResult<T> {
    private int code;
    private String msg;
    private T data;

    public static AjaxResult success() {
        AjaxResult result = new AjaxResult();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    public static AjaxResult success(Object data) {
        AjaxResult result = new AjaxResult();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static AjaxResult failure(ResultCode resultCode) {
        AjaxResult result = new AjaxResult();
        result.setResultCode(resultCode);
        return result;
    }

    public static AjaxResult failure(ResultCode resultCode, Object data) {
        AjaxResult result = new AjaxResult();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

    private void setResultCode(ResultCode resultCode) {
        this.code = resultCode.code();
        this.msg = resultCode.message();
    }
}
