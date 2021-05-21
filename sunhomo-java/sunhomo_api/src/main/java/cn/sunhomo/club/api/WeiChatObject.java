package cn.sunhomo.club.api;

import lombok.Data;

@Data
public class WeiChatObject {
    private String openid;
    private String session_key;
    private String unionid;
    private String errmsg;
    private int errcode;

}
