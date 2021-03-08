package cn.sunhomo.club.quartz;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunBattle;
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
import java.util.List;

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
//                List<SunBattle> battles;
//                battleService.batchInsert(battles);
            }
        }
        log.info("-------完成自动化组建盲盒约战-------");
    }
}
