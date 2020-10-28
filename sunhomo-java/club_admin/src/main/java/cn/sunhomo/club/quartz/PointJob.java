package cn.sunhomo.club.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @author: Nickel Fang
 * @date: 2020/10/23 14:32
 */
public class PointJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //search the activities which finished last day
    }
}
