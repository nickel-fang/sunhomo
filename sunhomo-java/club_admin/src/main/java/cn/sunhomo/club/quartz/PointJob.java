package cn.sunhomo.club.quartz;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunMember;
import cn.sunhomo.club.domain.SunPointRecord;
import cn.sunhomo.club.service.ISunActivityService;
import cn.sunhomo.club.service.ISunPointService;
import cn.sunhomo.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author: Nickel Fang
 * @date: 2020/10/23 14:32
 */
@Slf4j
public class PointJob extends QuartzJobBean {
    @Autowired
    private ISunActivityService activityService;

    @Autowired
    private ISunPointService pointService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("-------开始自动化处理积分累积-------");
        //search the activities which finished last day
        String yesterday = LocalDate.now().plusDays(-1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        SunActivity activity = new SunActivity();
        activity.setActivityDate(yesterday);
        activity.setActivityState((byte) 3);

        List<SunActivity> activities = activityService.selectActivities(activity);

        Date now = new Date();

        for (SunActivity activity1 : activities) {
            int number = activity1.getNumbers(); //活动设定人数
            List<SunMember> members = activity1.getMembers();

            //记录所有需要更新积分的会员
            Map<Integer, SunMember> updateMembers = new HashMap<>();
            //记录所有需要插入的积分明细
            List<SunPointRecord> insertPointRecords = new ArrayList<SunPointRecord>();

            SunMember member;

            for (int i = 0; i < (number > members.size() ? members.size() : number); i++) {
                member = members.get(i);
                if (updateMembers.get(member.getMemberId()) == null) {
                    //首次报名，奖励2分
                    if (member.getTotalPoint() == 0) {
                        insertPointRecords.add(new SunPointRecord(null, member.getMemberId(), (byte) 5, "系统奖励：首次报名", 2, now));
                        member.addPoint(2);
                    }
                    updateMembers.put(member.getMemberId(), member);
                }

                if (activity1.getActivityType() == 2) {
                    //比赛活动，每个加2分，挂加1分
                    if (member.getIsMaster() == 0) {
                        insertPointRecords.add(new SunPointRecord(null, member.getMemberId(), (byte) 2, "比赛活动：本人报名", 2, now));
                        updateMembers.get(member.getMemberId()).addPoint(2);
                    } else { //比分支应该不会出现，比赛报名逻辑已控制不能带挂报名参加比赛
                        insertPointRecords.add(new SunPointRecord(null, member.getMemberId(), (byte) 2, "比赛活动：带挂报名", 1, now));
                        updateMembers.get(member.getMemberId()).addPoint(1);
                    }

                } else if (activity1.getActivityType() == 1) {
                    //72小时之前报名，加2分，其他时间报名加1分，挂加1分
                    if (member.getIsMaster() == 0) {
                        LocalDateTime activityStartTime = LocalDateTime.parse(activity1.getActivityDate() + " " + activity1.getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                        if (DateUtils.asLocalDateTime(member.getEnrollTime()).isBefore(activityStartTime.plusDays(-3))) {
                            insertPointRecords.add(new SunPointRecord(null, member.getMemberId(), (byte) 1, "打球活动：本人早鸟报名", 2, now));
                            updateMembers.get(member.getMemberId()).addPoint(2);
                        } else {
                            insertPointRecords.add(new SunPointRecord(null, member.getMemberId(), (byte) 1, "打球活动：本人报名", 1, now));
                            updateMembers.get(member.getMemberId()).addPoint(1);
                        }
                    } else {
                        insertPointRecords.add(new SunPointRecord(null, member.getMemberId(), (byte) 1, "打球活动：带挂报名", 1, now));
                        updateMembers.get(member.getMemberId()).addPoint(1);
                    }
                }
            }

            pointService.updateMembersPoint(new ArrayList<SunMember>(updateMembers.values()), insertPointRecords);
        }
        log.info("-------完成自动化处理积分累积-------");
    }
}
