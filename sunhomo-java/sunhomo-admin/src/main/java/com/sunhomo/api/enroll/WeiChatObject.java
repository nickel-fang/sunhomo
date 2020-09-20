package com.sunhomo.api.enroll;

import lombok.Data;

@Data
public class WeiChatObject {
    private String openid;
    private String session_key;
    private String unionid;
    private String errmsg;
    private int errcode;

}
