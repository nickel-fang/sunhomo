package cn.sunhomo.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableDataInfo {
    //总记录数
    private long total;
    //列表数据
    private List<?> rows;
    //消息状态码
    private int code;
    //消息内容
    private String msg;
}
