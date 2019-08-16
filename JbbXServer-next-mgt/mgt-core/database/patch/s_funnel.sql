
set @startdate= date(DATE_ADD(now(),INTERVAL -1 DAY));
set @enddate=date(now()); 

-- 点击PV
INSERT INTO  mgt_channel_pv (statistic_date,channel_code,h5_type,is_delegate,pv) 
select 
	@startdate as statistic_date, 
	source_id as channel_code, 
	event_value as h5_type, 
	event_value2 as delegate,
	count(distinct l1.cookie_id) as pv
from  mgt_user_event_logs l1
join  
(
	 select log.cookie_id, max(log.creation_date) as creation_date from mgt_user_event_logs log
	 where log.event_name = 'index' and log.event_action ='init'
	 and log.creation_date >= @startdate and  log.creation_date < @enddate
	 group by log.cookie_id
) l2 on l1.cookie_id = l2.cookie_id and l1.creation_date = l2.creation_date
join mgt_channels c on c.channel_code = l1.source_id
join mgt_accounts acc on c.creator = acc.account_id and acc.org_id=1
group by source_id, event_value, event_value2

ON DUPLICATE KEY UPDATE pv = VALUES(pv)
;


-- UV
INSERT INTO  mgt_channel_funnel (statistic_date,channel_code, u_type, h5_type,is_delegate,uv) 
select 
	@startdate , 
	source_id as channel_code,  
	u.u_type, 
	event_value as h5_type, 
	event_value2 as delegate, 
	count(distinct l1.user_id) as uv 
from  mgt_user_event_logs l1
join  
(
	 select log.cookie_id, max(log.creation_date) as creation_date from mgt_user_event_logs log
	 where log.event_name = 'index' and log.event_action ='submit'
	 and log.creation_date > @startdate and  log.creation_date < @enddate
	 group by log.cookie_id
) l2 on l1.cookie_id = l2.cookie_id and l1.creation_date = l2.creation_date
join mgt_channels c on c.channel_code = l1.source_id
join mgt_accounts acc on c.creator = acc.account_id and acc.org_id=1
join (
	select user_id, creation_date,   
	case when date(creation_date) = date(@startdate) then 1 else 0 end as u_type
	from mgt_users  
     ) u on u.user_id = l1.user_id
group by source_id, event_value, event_value2, u.u_type

ON DUPLICATE KEY UPDATE uv = VALUES(uv)
;


-- addmore init

INSERT INTO  mgt_channel_funnel (statistic_date,channel_code, u_type, h5_type,is_delegate,addmore_init_cnt) 
select 
	@startdate , 
	source_id as channel_code,  
	u.u_type, 
	event_value as h5_type, 
	event_value2 as delegate, 
	count(distinct l1.user_id) as addmore_init_cnt 
from  mgt_user_event_logs l1
join  
(
	 select log.cookie_id, max(log.creation_date) as creation_date from mgt_user_event_logs log
	 where log.event_name = 'addmore' and log.event_action ='init'
	 and log.creation_date > @startdate and  log.creation_date < @enddate
	 group by log.cookie_id
) l2 on l1.cookie_id = l2.cookie_id and l1.creation_date = l2.creation_date
join mgt_channels c on c.channel_code = l1.source_id
join mgt_accounts acc on c.creator = acc.account_id and acc.org_id=1
join (
	select user_id, creation_date,   
	case when date(creation_date) = date(@startdate) then 1 else 0 end as u_type
	from mgt_users  
     ) u on u.user_id = l1.user_id
group by source_id, event_value, event_value2, u.u_type

ON DUPLICATE KEY UPDATE addmore_init_cnt = VALUES(addmore_init_cnt)
;


-- addmore submit

INSERT INTO  mgt_channel_funnel (statistic_date,channel_code, u_type, h5_type,is_delegate,addmore_submit_cnt) 
select 
	@startdate , 
	source_id as channel_code,  
	u.u_type, 
	event_value as h5_type, 
	event_value2 as delegate, 
	count(distinct l1.user_id) as addmore_submit_cnt 
from  mgt_user_event_logs l1
join  
(
	 select log.cookie_id, max(log.creation_date) as creation_date from mgt_user_event_logs log
	 where log.event_name = 'addmore' and log.event_action ='submit'
	 and log.creation_date > @startdate and  log.creation_date < @enddate
	 group by log.cookie_id
) l2 on l1.cookie_id = l2.cookie_id and l1.creation_date = l2.creation_date
join mgt_channels c on c.channel_code = l1.source_id
join mgt_accounts acc on c.creator = acc.account_id and acc.org_id=1
join (
	select user_id, creation_date,   
	case when date(creation_date) = date(@startdate) then 1 else 0 end as u_type
	from mgt_users  
     ) u on u.user_id = l1.user_id
group by source_id, event_value, event_value2, u.u_type

ON DUPLICATE KEY UPDATE addmore_submit_cnt = VALUES(addmore_submit_cnt)
;

-- success init

INSERT INTO  mgt_channel_funnel (statistic_date,channel_code, u_type, h5_type,is_delegate,success_init_cnt) 
select 
	@startdate , 
	source_id as channel_code,  
	u.u_type, 
	event_value as h5_type, 
	event_value2 as delegate, 
	count(distinct l1.user_id) as success_init_cnt 
from  mgt_user_event_logs l1
join  
(
	 select log.cookie_id, max(log.creation_date) as creation_date from mgt_user_event_logs log
	 where log.event_name = 'success' and log.event_action ='init'
	 and log.creation_date > @startdate and  log.creation_date < @enddate
	 group by log.cookie_id
) l2 on l1.cookie_id = l2.cookie_id and l1.creation_date = l2.creation_date
join mgt_channels c on c.channel_code = l1.source_id
join mgt_accounts acc on c.creator = acc.account_id and acc.org_id=1
join (
	select user_id, creation_date,   
	case when date(creation_date) = date(@startdate) then 1 else 0 end as u_type
	from mgt_users  
     ) u on u.user_id = l1.user_id
group by source_id, event_value, event_value2, u.u_type

ON DUPLICATE KEY UPDATE success_init_cnt = VALUES(success_init_cnt)
;



-- success download

INSERT INTO  mgt_channel_funnel (statistic_date,channel_code, u_type, h5_type,is_delegate,success_download_cnt) 
select 
	@startdate , 
	source_id as channel_code,  
	u.u_type, 
	event_value as h5_type, 
	event_value2 as delegate, 
	count(distinct l1.user_id) as success_download_cnt 
from  mgt_user_event_logs l1
join  
(
	 select log.cookie_id, max(log.creation_date) as creation_date from mgt_user_event_logs log
	 where log.event_name = 'success' and log.event_action ='download'
	 and log.creation_date > @startdate and  log.creation_date < @enddate
	 group by log.cookie_id
) l2 on l1.cookie_id = l2.cookie_id and l1.creation_date = l2.creation_date
join mgt_channels c on c.channel_code = l1.source_id
join mgt_accounts acc on c.creator = acc.account_id and acc.org_id=1
join (
	select user_id, creation_date,   
	case when date(creation_date) = date(@startdate) then 1 else 0 end as u_type
	from mgt_users  
     ) u on u.user_id = l1.user_id
group by source_id, event_value, event_value2, u.u_type

ON DUPLICATE KEY UPDATE success_download_cnt = VALUES(success_download_cnt)
;


-- app login
INSERT INTO  mgt_channel_funnel (statistic_date,channel_code, u_type, h5_type,is_delegate,app_login_cnt) 
select 
	@startdate , 
	l1.source_id as channel_code,  
	u.u_type, 
	l1.event_value as h5_type, 
	l1.event_value2 as delegate, 
	count(distinct l1.user_id) as app_login_cnt 
from  mgt_user_event_logs l1
join
(
	 select log.cookie_id, max(log.creation_date) as creation_date from mgt_user_event_logs log
	 where log.event_name = 'index' and log.event_action ='submit'
	 and log.creation_date >= @startdate  and  log.creation_date < @endDate 
	 group by log.cookie_id
) l2 on l1.cookie_id = l2.cookie_id and l1.creation_date = l2.creation_date
join mgt_channels c on c.channel_code = l1.source_id
join mgt_accounts acc on c.creator = acc.account_id and acc.org_id=1
join (
	select user_id, creation_date,   jbb_user_id,
	case when date(creation_date) = date(@startdate) then 1 else 0 end as u_type
	from mgt_users  
     ) u on u.user_id = l1.user_id
join jbbserver.user_event_logs l3 on l3.user_id = u.jbb_user_id and l3.event_name = 'user' AND l3.event_action = 'login'
where l3.creation_date > @startdate and  l3.creation_date < @endDate 
group by l1.source_id, l1.event_value, l1.event_value2, u.u_type

ON DUPLICATE KEY UPDATE app_login_cnt = VALUES(app_login_cnt)
;

-- 贷超PV, uV, 产品UV



INSERT INTO  mgt_channel_funnel (statistic_date,channel_code, u_type, h5_type,is_delegate,p_click_cnt,p_click_uv,p_uv) 
select 
	@startdate , 
	l1.source_id as channel_code,  
	u.u_type, 
	l1.event_value as h5_type, 
	l1.event_value2 as delegate, 
	sum(l4.platformPv) as p_click_cnt , 
	sum(l4.uv) as p_click_uv , 
	sum(l4.platformUv) as p_uv 
from  mgt_user_event_logs l1
join
(
	 select log.cookie_id, max(log.creation_date) as creation_date from mgt_user_event_logs log
	 where log.event_name = 'index' and log.event_action ='submit'
	 and log.creation_date >= @startdate and  log.creation_date < @enddate
	 group by log.cookie_id
) l2 on l1.cookie_id = l2.cookie_id and l1.creation_date = l2.creation_date
join mgt_channels c on c.channel_code = l1.source_id
join mgt_accounts acc on c.creator = acc.account_id and acc.org_id=1
join (
	select user_id, creation_date,   jbb_user_id,
	case when date(creation_date) = date(@startdate) then 1 else 0 end as u_type
	from mgt_users  
     ) u on u.user_id = l1.user_id
join jbbserver.user_event_logs l3 on l3.user_id = u.jbb_user_id and l3.event_name = 'user' AND l3.event_action = 'login'
join 
    (
	select user_id, 
	    COUNT(1) AS platformPv ,
	    COUNT(DISTINCT user_id) AS uv ,
	    COUNT( DISTINCT user_id, cookie_id) AS platformUv
	  from mgt_user_event_logs
	  where creation_date >= @startdate and  creation_date < @enddate and event_name = 'daichao'
	  group by user_id
    ) l4 on l4.user_id = u.jbb_user_id 
where l3.creation_date >= @startdate and  l3.creation_date < @enddate
group by l1.source_id, l1.event_value, l1.event_value2, u.u_type

ON DUPLICATE KEY UPDATE p_click_cnt = VALUES(p_click_cnt), p_click_uv = VALUES(p_click_uv), p_uv = VALUES(p_uv)
;
