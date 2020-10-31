package cn.sunhomo.club.config;

import cn.sunhomo.club.quartz.ActivityJob;
import cn.sunhomo.club.quartz.PointJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Nickel Fang
 * @date: 2020/10/23 14:43
 */
@Configuration
public class QuartzConfig {
    @Value("${club.job.point}")
    private String jobCronForPoint;

    @Value("${club.job.activity}")
    private String jobCronForActivity;

    @Bean
    public JobDetail pointJobDetail() {
        return JobBuilder.newJob(PointJob.class).withIdentity("pointJob").storeDurably().build();
    }

    @Bean
    public Trigger pointJobTrigger() {
        return TriggerBuilder.newTrigger().forJob(pointJobDetail())
                .withIdentity("pointJob")
                .withSchedule(CronScheduleBuilder.cronSchedule(jobCronForPoint))
                .build();
    }

    @Bean
    public JobDetail activityJobDetail() {
        return JobBuilder.newJob(ActivityJob.class).withIdentity("activityJob").storeDurably().build();
    }

    @Bean
    public Trigger activityJobTrigger() {
        return TriggerBuilder.newTrigger().forJob(activityJobDetail())
                .withIdentity("activityJob")
                .withSchedule(CronScheduleBuilder.cronSchedule(jobCronForActivity))
                .build();
    }
}
