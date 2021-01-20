package cn.sunhomo.club.quartz;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunBattle;
import cn.sunhomo.club.domain.SunMember;
import cn.sunhomo.club.domain.SunPointRecord;
import cn.sunhomo.club.service.ISunActivityService;
import cn.sunhomo.club.service.ISunBattleService;
import cn.sunhomo.club.service.ISunMemberService;
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
import java.util.stream.Collectors;

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

    @Autowired
    private ISunBattleService battleService;

    @Autowired
    private ISunMemberService memberService;

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
                        insertPointRecords.add(new SunPointRecord(null, member.getMemberId(), (byte) 5, "系统奖励：小程序首次报名", 2, now));
                        member.addAllPoint(2);
                    }
                    updateMembers.put(member.getMemberId(), member);
                }

                if (activity1.getActivityType() == 2) {
                    //比赛活动，每个加2分，挂加1分
                    if (member.getIsMaster() == 0) {
                        insertPointRecords.add(new SunPointRecord(null, member.getMemberId(), (byte) 2, "比赛活动：本人报名", 2, now));
                        updateMembers.get(member.getMemberId()).addAllPoint(2);
                    } else { //比分支应该不会出现，比赛报名逻辑已控制不能带挂报名参加比赛
                        insertPointRecords.add(new SunPointRecord(null, member.getMemberId(), (byte) 2, "比赛活动：带挂报名", 1, now));
                        updateMembers.get(member.getMemberId()).addAllPoint(1);
                    }

                } else if (activity1.getActivityType() == 1) {
                    //72小时之前报名，加2分，其他时间报名加1分，挂加1分
                    if (member.getIsMaster() == 0) {
                        LocalDateTime activityStartTime = LocalDateTime.parse(activity1.getActivityDate() + " " + activity1.getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                        if (DateUtils.asLocalDateTime(member.getEnrollTime()).isBefore(activityStartTime.plusDays(-3))) {
                            insertPointRecords.add(new SunPointRecord(null, member.getMemberId(), (byte) 1, "打球活动：本人早鸟报名", 2, now));
                            updateMembers.get(member.getMemberId()).addAllPoint(2);
                        } else {
                            insertPointRecords.add(new SunPointRecord(null, member.getMemberId(), (byte) 1, "打球活动：本人报名", 1, now));
                            updateMembers.get(member.getMemberId()).addAllPoint(1);
                        }
                    } else {
                        insertPointRecords.add(new SunPointRecord(null, member.getMemberId(), (byte) 1, "打球活动：带挂报名", 1, now));
                        updateMembers.get(member.getMemberId()).addAllPoint(1);
                    }
                }
            }


            //处理约战积分
            List<SunBattle> battles = battleService.selectBattlesByActivityId(activity1.getActivityId());
            for (SunBattle battle : battles) {
                SunMember a1 = updateMembers.get(battle.getA1());
                SunMember a2 = updateMembers.get(battle.getA2());
                SunMember b1 = updateMembers.get(battle.getB1());
                SunMember b2 = updateMembers.get(battle.getB2());

                //恢复暂扣实时积分
                a1.addRealPoint(battle.getBattlePoint());
                a2.addRealPoint(battle.getBattlePoint());
                b1.addRealPoint(battle.getBattlePoint());
                b2.addRealPoint(battle.getBattlePoint());

                //成功的约战，才调整积分
                if (battle.getBattleState() == 2) {

                    //A胜
                    if (battle.getBattleResult() == 1) {
                        a1.addYearAndRealPoint(battle.getBattlePoint());
                        a2.addYearAndRealPoint(battle.getBattlePoint());
                        b1.addYearAndRealPoint(-battle.getBattlePoint());
                        b2.addYearAndRealPoint(-battle.getBattlePoint());

                        //修改战绩
                        a1.setWinNumber(a1.getWinNumber() + 1);
                        a1.setRatio((short) (a1.getWinNumber() * 10000 / (a1.getWinNumber() + a1.getLoseNumber())));

                        //A2不为A1的挂时
                        if (battle.getA1().intValue() != battle.getA2().intValue()) {
                            a2.setWinNumber(a2.getWinNumber() + 1);
                            a2.setRatio((short) (a2.getWinNumber() * 10000 / (a2.getWinNumber() + a2.getLoseNumber())));
                            //积分记录
                            insertPointRecords.add(new SunPointRecord(null, a1.getMemberId(), (byte) 3, "约战：胜", battle.getBattlePoint(), now));
                            insertPointRecords.add(new SunPointRecord(null, a2.getMemberId(), (byte) 3, "约战：胜", battle.getBattlePoint(), now));
                        } else {
                            //积分记录
                            insertPointRecords.add(new SunPointRecord(null, a1.getMemberId(), (byte) 3, "约战：胜", battle.getBattlePoint() * 2, now));
                        }

                        b1.setLoseNumber(b1.getLoseNumber() + 1);
                        b1.setRatio((short) (b1.getWinNumber() * 10000 / (b1.getWinNumber() + b1.getLoseNumber())));

                        if (battle.getB1().intValue() != battle.getB2().intValue()) {
                            b2.setLoseNumber(b2.getLoseNumber() + 1);
                            b2.setRatio((short) (b2.getWinNumber() * 10000 / (b2.getWinNumber() + b2.getLoseNumber())));
                            insertPointRecords.add(new SunPointRecord(null, b1.getMemberId(), (byte) 3, "约战：负", -battle.getBattlePoint(), now));
                            insertPointRecords.add(new SunPointRecord(null, b2.getMemberId(), (byte) 3, "约战：负", -battle.getBattlePoint(), now));
                        } else {
                            insertPointRecords.add(new SunPointRecord(null, b1.getMemberId(), (byte) 3, "约战：负", -battle.getBattlePoint() * 2, now));
                        }
                    } else if (battle.getBattleResult() == -1) {
                        a1.addYearAndRealPoint(-battle.getBattlePoint());
                        a2.addYearAndRealPoint(-battle.getBattlePoint());
                        b1.addYearAndRealPoint(battle.getBattlePoint());
                        b2.addYearAndRealPoint(battle.getBattlePoint());

                        //修改战绩
                        a1.setLoseNumber(a1.getLoseNumber() + 1);
                        a1.setRatio((short) (a1.getWinNumber() * 10000 / (a1.getWinNumber() + a1.getLoseNumber())));

                        //A2不为A1的挂时
                        if (battle.getA1().intValue() != battle.getA2().intValue()) {
                            a2.setLoseNumber(a2.getLoseNumber() + 1);
                            a2.setRatio((short) (a2.getWinNumber() * 10000 / (a2.getWinNumber() + a2.getLoseNumber())));
                            //积分记录
                            insertPointRecords.add(new SunPointRecord(null, a1.getMemberId(), (byte) 3, "约战：负", -battle.getBattlePoint(), now));
                            insertPointRecords.add(new SunPointRecord(null, a2.getMemberId(), (byte) 3, "约战：负", -battle.getBattlePoint(), now));
                        } else {
                            insertPointRecords.add(new SunPointRecord(null, a1.getMemberId(), (byte) 3, "约战：负", -battle.getBattlePoint() * 2, now));
                        }

                        b1.setWinNumber(b1.getWinNumber() + 1);
                        b1.setRatio((short) (b1.getWinNumber() * 10000 / (b1.getWinNumber() + b1.getLoseNumber())));

                        if (battle.getB1().intValue() != battle.getB2().intValue()) {
                            b2.setWinNumber(b2.getWinNumber() + 1);
                            b2.setRatio((short) (b2.getWinNumber() * 10000 / (b2.getWinNumber() + b2.getLoseNumber())));
                            insertPointRecords.add(new SunPointRecord(null, b1.getMemberId(), (byte) 3, "约战：胜", battle.getBattlePoint(), now));
                            insertPointRecords.add(new SunPointRecord(null, b2.getMemberId(), (byte) 3, "约战：胜", battle.getBattlePoint(), now));
                        } else {
                            insertPointRecords.add(new SunPointRecord(null, b1.getMemberId(), (byte) 3, "约战：胜", battle.getBattlePoint() * 2, now));
                        }
                    }
                }
            }

            pointService.updateMembersPoint(new ArrayList<SunMember>(updateMembers.values()), insertPointRecords);

            //处理约战打CALL积分
            for (SunBattle battle : battles) {
                //取消的约战，在取消时即时恢复积分了
                //待确认，只需恢复打CALLER者的暂扣积分
                if (battle.getBattleState() == 1 && battle.getVotes().size() > 0) {
                    memberService.addRealPoint(battle.getVotes().stream().map(v -> v.getMemberId()).collect(Collectors.toList()), 1);
                } else if (battle.getBattleState() == 2 && battle.getVotes().size() > 0) {

                    if (battle.getBattleResult() == null) {
                        memberService.addRealPoint(battle.getVotes().stream().map(v -> v.getMemberId()).collect(Collectors.toList()), 1);
                    } else {
                        List<Integer> memberIds = battle.getVotes().stream().filter(v -> v.getVote() == battle.getBattleResult()).map(v -> v.getMemberId()).collect(Collectors.toList());
                        if (memberIds.size() > 0)
                            memberService.addRealPoint(memberIds, 2);
                        pointService.insertPointRecords(
                                battle.getVotes().stream()
                                        .map(v -> new SunPointRecord(null, v.getMemberId(), (byte) 103, v.getVote() == battle.getBattleResult() ? "约战打CALL：胜" : "约战打CALL：负", v.getVote() == battle.getBattleResult() ? 1 : -1, now))
                                        .collect(Collectors.toList())
                        );

                    }

                }
            }


        }

        log.info("-------完成自动化处理积分累积-------");
    }
}
