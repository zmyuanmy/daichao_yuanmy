-- 0505 渠道包漏斗统计
CREATE TABLE mgt_channel_app_funnel (
  statistic_date date NOT NULL  COMMENT '统计时间',
  channel_code varchar(20) NOT NULL COMMENT '渠道编号 ',
  login_num int(11) DEFAULT '0' COMMENT '总登录数',
  login_num_new int(11) DEFAULT '0' COMMENT '新用户登录数',
  login_num_old int(11) DEFAULT '0' COMMENT '老用户登录数',
  uv_cnt int(11) DEFAULT '0' COMMENT 'UV数',
  uv_cnt_new int(11) DEFAULT '0' COMMENT '新用户UV数',
  uv_cnt_old int(11) DEFAULT '0' COMMENT '老用户UV数',
  puv_cnt int(11) DEFAULT '0' COMMENT '老用户登录数',
  puv_cnt_new int(11) DEFAULT '0' COMMENT '新用户产品UV数',
  puv_cnt_old int(11) DEFAULT '0' COMMENT '老用户产品UV数',
  update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP() COMMENT '更新日期',
  PRIMARY KEY (statistic_date, channel_code )
) ;


alter table mgt_channel_app_funnel add index i_mgt_channel_app_funnel_sdate (statistic_date);
alter table mgt_channel_app_funnel add index i_mgt_channel_app_funnel_ccode (channel_code);

-- 统计

SET @START = DATE_ADD(now() , INTERVAL - 1 DAY);
SET @startdate = date(@START);
SET @enddate = date(DATE_ADD(@START , INTERVAL 1 DAY));

-- -- 当天的新用户
-- alter table tmp_app_funnel_new_users add index i_tmp_app_funnel_new_users_uid (user_id);
-- alter table tmp_app_funnel_new_users add index i_tmp_app_funnel_new_users_cdate (creation_date);

 truncate tmp_app_funnel_new_users;
 insert into tmp_app_funnel_new_users
-- drop table if EXISTS tmp_app_funnel_new_users;
-- create table tmp_app_funnel_new_users
SELECT
	jbbu.user_id, jbbu.platform  as channel_code,  jbbu.creation_date
FROM jbbserver.users jbbu
WHERE jbbu.creation_date > @startdate AND jbbu.creation_date < @endDate
	AND jbbu.platform in ('jbb-oppo-1503')
;


-- 当天登录的所有用户

-- alter table tmp_app_funnel_login_users add index i_tmp_app_funnel_login_users_uid (user_id);
-- alter table tmp_app_funnel_login_users add index i_tmp_app_funnel_login_users_cdate (creation_date);
-- alter table tmp_app_funnel_login_users add index i_tmp_app_funnel_login_users_ccode (channel_code);

 truncate tmp_app_funnel_login_users;
 insert into  tmp_app_funnel_login_users
-- drop table if EXISTS tmp_app_funnel_login_users;
-- create table tmp_app_funnel_login_users
select log.user_id , log.creation_date, log.event_value as channel_code
from jbbserver.user_event_logs log 
join (
	select log.user_id , min(log.creation_date) as creation_date
	from jbbserver.user_event_logs log 
	where log.event_action = 'login' and log.event_value in ('jbb-oppo-1503')
	and log.creation_date > @startdate AND log.creation_date < @endDate
	group by log.user_id
) t on t.user_id = log.user_id and t.creation_date = log.creation_date
where log.event_action = 'login' and log.event_value in ('jbb-oppo-1503')
and log.creation_date > @startdate AND log.creation_date < @endDate
;


-- 当天用户 点击情况
-- alter table tmp_app_funnel_p_users add index i_tmp_app_funnel_p_users_uid (user_id);

 truncate tmp_app_funnel_p_users;
 insert into tmp_app_funnel_p_users 
-- drop table if EXISTS tmp_app_funnel_p_users;
-- create table tmp_app_funnel_p_users
select 
	log.user_id AS user_id ,
	COUNT(1) AS platformPv ,
	COUNT(DISTINCT log.user_id) AS uv ,
	COUNT( DISTINCT log.user_id , log.cookie_id) AS platformUv
from mgt_user_event_logs log
where log.source_id in ('jbb-oppo-1503') and event_name = 'daichao'
and log.creation_date > @startdate AND log.creation_date < @endDate
group by log.user_id
;

-- 更新数据
delete from mgt_channel_app_funnel where statistic_date = @startdate;
insert into mgt_channel_app_funnel
select
@startdate,
t1.channel_code
, count(t1.user_id) as login_num
, sum(case when t2.user_id is not null then 1 else 0 end) as login_num_new
, sum(case when t2.user_id is  null then 1 else 0 end) as login_num_old
, sum(t3.uv) as uv_cnt
, sum(case when t2.user_id is not null then t3.uv else 0 end) as uv_cnt_new
, sum(case when t2.user_id is  null then t3.uv else 0 end) as uv_cnt_old
, sum(t3.platformUv) as puv_cnt
, sum(case when t2.user_id is not null then t3.platformUv else 0 end) as puv_cnt_new
, sum(case when t2.user_id is  null then t3.platformUv else 0 end) as puv_cnt_old
, now()
from tmp_app_funnel_login_users t1
left join tmp_app_funnel_new_users t2 on t1.user_id = t2.user_id
left join tmp_app_funnel_p_users t3 on t1.user_id = t3.user_id
group by t1.channel_code
;