package cn.sunhomo.club.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * SUN_BATTLE_VOTE
 * @author 
 */
@Data
public class SunBattleVote extends BaseEntity {
    private Integer battleId;

    private Integer memberId;

    private String memberPhoto;

    /**
     * 支持谁， 1支持A队，-1支持B队
     */
    private Byte vote;

    private static final long serialVersionUID = 1L;
}