# 活动表

drop table if exists SUN_ACTIVITY;
create table SUN_ACTIVITY
(
    ACTIVITY_ID    int auto_increment,
    ACTIVITY_NAME  varchar(32) comment '活动名称',
    ACTIVITY_TYPE  tinyint comment '活动类型， 1：普通打球活动 2：比赛活动',
    ACTIVITY_DATE  VARCHAR(10) comment '活动日期',
    START_TIME     VARCHAR(8) comment '活动开始时间',
    END_TIME       VARCHAR(8) comment '活动结束时间',
    DEADLINE       VARCHAR(8) comment '活动报名、抽签截止时间',
    PLACE          varchar(32) comment '活动地点',
    FIELD          varchar(32) comment '比赛场地，如7:8:9',
    ACTIVITY_RULE  varchar(256) comment '活动规则',
    NUMBERS        int comment '活动人数',
    MEMO           varchar(256) comment '备注',
    ACTIVITY_STATE tinyint comment '活动状态， 1：报名中， 2：活动中， 3：已结束， 4：已取消',
    primary key (ACTIVITY_ID),
    key (ACTIVITY_DATE desc)
);

#会员表
drop table if exists SUN_MEMBER;
create table SUN_MEMBER
(
    MEMBER_ID    int auto_increment,
    OPENID       varchar(32) comment '用户微信小程序唯一身份',
    MEMBER_NAME  varchar(32) comment '用户名，默认微信昵称',
    MEMBER_PHOTO varchar(256) comment '用户图像，直接取微信图片',
    TOTAL_POINT  int default 0 comment '总积分，累积参加活动及比赛的积分',
    YEAR_POINT   int default 0 comment '当年总积分，累积当年参加活动及比赛的积分',
    POINT        int default 0 comment '实时积分，参加活动及比赛、约战、消费的积分',
    WIN_NUMBER   int default 0 comment '约战胜局',
    LOSE_NUMBER  int default 0 comment '约战负局',
    RATIO        smallint default 0 comment '胜率 = WIN_NUMBER/(WIN_NUMBER+LOSE_NUMBER)，取4位数，如98.33%，数据存中9833',
    SIGN_DATE    date comment '注册日期',
    primary key (MEMBER_ID),
    key (OPENID),
    key (RATIO desc, WIN_NUMBER desc)
);

#分组表，用于比赛抽签分组
drop table if exists SUN_DIVISION;
create table SUN_DIVISION
(
    DIVISION_ID   int auto_increment,
    ACTIVITY_ID   int comment '所属活动 ACTIVITY.ACTIVITY_ID',
    DIVISION_NAME varchar(32) comment '分组名',
    LEADER        int comment '队长， MEMBER.MEMBER_ID',
    LEADER_NAME   varchar(32) comment '冗余字段，队长名',
    RANK          tinyint comment '名次， 1为冠军， 2为亚军，3为季军，后面直接显示参与',
    primary key (DIVISION_ID),
    key (ACTIVITY_ID)
);

#会员报名活动关联表
#一场活动，存在一人多报的情况，如在别处， 在别处+1， 在别处+2
drop table if exists SUN_MEMBER_TO_ACTIVITY;
create table SUN_MEMBER_TO_ACTIVITY
(
    MEMBER_ID   int,
    ACTIVITY_ID int,
    ENROLL_TIME datetime comment '报名时间，按照报名时间显示排序',
    IS_MASTER tinyint comment '主报名者，0为当人报名，1，2...分别为其挂',
    key (ACTIVITY_ID, MEMBER_ID)
);

#会员比赛分组表
drop table if exists SUN_MEMBER_TO_DIVISION;
create table SUN_MEMBER_TO_DIVISION
(
    MEMBER_ID   int,
    DIVISION_ID int
);

#商品表
drop table if exists SUN_GOOD;
create table SUN_GOOD
(
    GOOD_ID    int auto_increment,
    GOOD_NAME  varchar(32) comment '商品名称',
    GOOD_PHOTO varchar(128) comment '商品图片',
    VALUE      int comment '兑换所需积分',
    STOCK      int comment '库存量',
    EXCHANGE   int comment '兑换次数，用于展现商品热度',
    primary key (GOOD_ID)
);

#商品兑换记录表
drop table if exists SUN_GOOD_TRANSACTION;
create table SUN_GOOD_TRANSACTION
(
    GOOD_TRANSACTION_ID   int auto_increment,
    GOOD_ID               int comment '商品ID，GOOD.GOOD_ID',
    MEMBER_ID             int comment '商品兑换人， MEMBER.MEMBER_ID',
    GOOD_TRANSACTION_DATE date comment '商品兑换日期',
    primary key (GOOD_TRANSACTION_ID),
    key (MEMBER_ID)
);

#会员积分明细表
drop table if exists SUN_POINT_RECORD;
create table SUN_POINT_RECORD
(
    POINT_RECORD_ID    int auto_increment,
    MEMBER_ID          int comment '会员ID',
    POINT_RECORD_TYPE  tinyint comment '积明交易类型 1：参加打球活动 2：参加比赛活动 3：约战 4：兑换商品 5:系统奖励积分',
    POINT_RECORD_VALUE int comment '积明交易额，获得积分为正数，消费积分为数',
    POINT_RECORD_DATE  date comment '交易日期',
    primary key (POINT_RECORD_ID),
    key (MEMBER_ID)
);

#约战表（只保留一年的约战记录）
drop table if exists SUN_BATTLE;
create table SUN_BATTLE
(
    BATTLE_ID     int auto_increment,
    ACTIVITY_ID   int comment '所属活动, ACTIVITY.ACTIVITY_ID',
    A1            int comment '选手A1，MEMBER.MEMBER_ID',
    A2            int comment '选手A2，MEMBER.MEMBER_ID',
    B1            int comment '选手B1，MEMBER.MEMBER_ID',
    B2            int comment '选手B2，MEMBER.MEMBER_ID',
    A1_NAME       varchar(32),
    A2_NAME       varchar(32),
    B1_NAME       varchar(32),
    B2_NAME       varchar(32),
    BATTLE_DATE   varchar(10) comment '约战的日期，冗余字段，可取ACTIVITY.ACTIVITY_DATE',
    BATTLE_POINT  int comment '约战的押注，多少个积分',
    BATTLE_STATE  int comment '约战的状态， -1 取消； 1111分别表示4位选手的应战确认，A1肯定为1，因为A1为发起者',
    BATTLE_RESULT tinyint comment '约战结束，1为A胜，-1为A负',
    primary key (BATTLE_ID),
    key (ACTIVITY_ID desc),
    key (BATTLE_DATE desc)
);

#约战记录表（备份一年前的约战记录）
drop table if exists SUN_BATTLE_HISTORY;
CREATE TABLE SUN_BATTLE_HISTORY AS SELECT * FROM SUN_BATTLE WHERE 1 = 0;




