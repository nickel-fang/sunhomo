package cn.sunhomo.club.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * SUN_AD
 * @author 
 */
@Data
public class SunAd implements Serializable {
    private Integer adId;

    /**
     * 广告名称
     */
    private String adName;

    /**
     * 广告图片URL
     */
    private String adImg;

    /**
     * 广告跳转的URL
     */
    private String adUrl;

    /**
     * 状态 1 显示， -1 不显示
     */
    private Byte adState;

    private static final long serialVersionUID = 1L;
}