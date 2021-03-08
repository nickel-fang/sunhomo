package cn.sunhomo.club.quartz;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunBattle;
import cn.sunhomo.club.domain.SunBlind;
import cn.sunhomo.club.service.ISunActivityService;
import cn.sunhomo.club.service.ISunBattleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Nickel Fang
 * @date: 2021/1/24 15:40
 */
@Slf4j
@Component
public class BattleJob extends QuartzJobBean {
    @Autowired
    private ISunBattleService battleService;

    @Autowired
    private ISunActivityService activityService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        /*log.info("-------开始自动化处理未成团的盲盒约战-------");
        List<SunBattle> battles = battleService.selectNotFormedBlindBattles(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
        for (SunBattle battle : battles) {
            battle.setBattleState(-1); //设置为取消
            battleService.confirmAndCancelAndWin(battle);
        }
        log.info("-------完成自动化处理未成团的盲盒约战-------");*/
        log.info("-------开始自动化组建盲盒约战-------");
        SunActivity activity = new SunActivity();
        activity.setActivityDate(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
        activity.setActivityType((byte) 1);
        List<SunActivity> activities = activityService.selectActivities(activity);
        if (activities.size() > 0) {
            activity = activities.get(0);
            //能组建多少个盲盒约战
            int blindBattles = activity.getBlindMembers().size() / 4;
            //TODO 随机分组
            if (blindBattles > 0) {
                List<SunBlind> blindMembers = activity.getBlindMembers().stream().limit(blindBattles * 4).collect(Collectors.toList());
                Collections.shuffle(blindMembers);
                List<SunBattle> battles = new ArrayList<>();
                SunBattle battle;
                for (int i = 0; i < blindBattles; i++) {
                    battle = new SunBattle();
                    battle.setActivityId(activity.getActivityId());
                    battle.setBattleType((byte) 2);
                    battle.setA1(blindMembers.get(i * 4 + 0).getMemberId());
                    battle.setA1Name(blindMembers.get(i * 4 + 0).getMemberName());
                    battle.setA2(blindMembers.get(i * 4 + 1).getMemberId());
                    battle.setA2Name(blindMembers.get(i * 4 + 1).getMemberName());
                    battle.setB1(blindMembers.get(i * 4 + 2).getMemberId());
                    battle.setB1Name(blindMembers.get(i * 4 + 2).getMemberName());
                    battle.setB2(blindMembers.get(i * 4 + 3).getMemberId());
                    battle.setB2Name(blindMembers.get(i * 4 + 3).getMemberName());
                    battle.setBattleDate(activity.getActivityDate());
                    battle.setBattlePoint(1);
                    battle.setBattleState(2);
                    battle.setIsPeak((byte) -1);
                    battle.setIsBlind((byte) 1);
                    battles.add(battle);
                }
                battleService.batchInsert(battles);
            }
        }
        log.info("-------完成自动化组建盲盒约战-------");
    }
}
