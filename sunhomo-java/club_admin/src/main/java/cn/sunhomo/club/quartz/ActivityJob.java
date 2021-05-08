package cn.sunhomo.club.quartz;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.service.ISunActivityService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author: Nickel Fang
 * @date: 2020/10/23 14:32
 */
@Slf4j
@Component
public class ActivityJob extends QuartzJobBean {
    @Autowired
    private ISunActivityService activityService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("-------开始自动化处理活动状态-------");
        SunActivity activity = new SunActivity();
        activity.setActivityState((byte) 1);
        List<SunActivity> activities = activityService.selectActivities(activity);
        for (SunActivity activity1 : activities) {
            //活动的结束时间已过，将该活动置为已结束
            if (LocalDateTime.now().isAfter(LocalDateTime.parse(activity1.getActivityDate() + " " + activity1.getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))) {
                activity1.setActivityState((byte) 3);
                activityService.updateActivity(activity1);
            }
            //删除对应的盲盒约战池
            activityService.deleteBlindBox(activity1.getActivityId());
        }
        log.info("-------完成自动化处理活动状态-------");
    }
}
