
SET @START = DATE_ADD(now() , INTERVAL -1 DAY);
SET @startdate = date(@START);
SET @enddate = date(DATE_ADD(@START , INTERVAL 1 DAY));
 
-- uv_cnt,download_click_cnt,app_login_cnt
INSERT INTO mgt_ip_statistics(statistic_date,ip_address,uv_cnt,download_click_cnt,app_login_cnt)
SELECT 
    @startdate as statistic_date,
    uel.remote_address_first AS ip_address, 
    count( DISTINCT uel.cookie_id ) AS uv_cnt,
    count( DISTINCT CASE WHEN uel.event_name = 'success' AND uel.event_action = 'download' THEN uel.cookie_id ELSE NULL END ) AS download_click_cnt,
    count( DISTINCT CASE WHEN uel.event_action = 'login' THEN uel.user_id ELSE NULL END ) AS app_login_cnt 
  FROM
    mgt_user_event_logs uel 
  WHERE
    uel.remote_address IS NOT NULL 
    AND uel.source_id IS NOT NULl
    AND ((uel.event_name = 'success' AND uel.event_action = 'download') OR (( uel.event_action = 'login')))
		AND uel.creation_date >= @startdate and  uel.creation_date < @enddate
  GROUP BY uel.remote_address_first
  ON DUPLICATE KEY UPDATE uv_cnt = VALUES(uv_cnt),download_click_cnt=VALUES(download_click_cnt),app_login_cnt=VALUES(app_login_cnt);

-- p_uv_cnt   p_click_cnt
INSERT INTO mgt_ip_statistics(statistic_date,ip_address,p_uv_cnt,p_click_cnt)	
 SELECT
    @startdate as statistic_date,
    uel.remote_address_first AS ip_address,
    COUNT( DISTINCT uel.user_id ) AS p_uv_cnt,
    COUNT( uel.user_id ) AS p_click_cnt 
  FROM
    mgt_user_event_logs uel 
  WHERE
    uel.remote_address IS NOT NULL 
    AND uel.event_name IN ( 'daichao', 'jbb2', 'bnh', 'xhyp' ) 
    AND uel.cookie_id != ''  AND uel.cookie_id != 'undefined' 
	AND uel.creation_date >= @startdate and  uel.creation_date < @enddate
  GROUP BY uel.remote_address_first
ON DUPLICATE KEY UPDATE p_uv_cnt = VALUES(p_uv_cnt),p_click_cnt=VALUES(p_click_cnt);

-- register_cnt
 INSERT INTO mgt_ip_statistics(statistic_date,ip_address,register_cnt)	
 SELECT @startdate as statistic_date,
	ip_address_first as ip_address  , 
	COUNT( 1 ) AS register_cnt 
 FROM mgt_users 
 WHERE ip_address_first IS NOT NULL 
 AND creation_date >= @startdate and  creation_date < @enddate
 GROUP BY ip_address_first
 ON DUPLICATE KEY UPDATE register_cnt = VALUES(register_cnt);
 
-- channel_sale
INSERT INTO mgt_ip_statistics(statistic_date,ip_address,channel_sale)	
SELECT 
	@startdate as statistic_date,
	remote_address AS ip_address
        ,GROUP_CONCAT( channelAndCreator SEPARATOR ';') AS channel_sale 
FROM(
   select 
	distinct uel.remote_address_first as remote_address 
	, concat_ws('-',c.channel_name,a.nickname) AS channelAndCreator
   from (
	select distinct remote_address_first, uel.source_id 
	from mgt_user_event_logs uel
	where uel.source_id is not null
		AND  uel.creation_date >= @startdate
		AND  uel.creation_date < @enddate
	union 
	select distinct u.ip_address_first, u.channel_code_first
	FROM mgt_users u
	where u.creation_date >= @startdate AND  u.creation_date < @enddate
   ) uel 
   left JOIN mgt_channels c ON c.channel_code = uel.source_id
   left JOIN mgt_accounts a ON c.creator = a.account_id  
   where  c.channel_code IS NOT NULL  AND  a.account_id IS NOT NULL 
) cs 
  GROUP BY cs.remote_address
ON DUPLICATE KEY UPDATE channel_sale = VALUES(channel_sale);