
-- 拆分用户表
-- alter table mgt_users auto_increment=100000000
-- password $argon2d$v=19$m=512,t=2048,p=1$tv94meKiixJUL56DIALOJQ$k0Z+X0nYrKEJL9YvzoVpaB5t0da+7VViuXUs/We7DME
-- update mgt_accounts set password = '$argon2d$v=19$m=512,t=2048,p=1$tv94meKiixJUL56DIALOJQ$k0Z+X0nYrKEJL9YvzoVpaB5t0da+7VViuXUs/We7DME

-- 创建库

-- DROP DATABASE jbbserverstg;
-- CREATE DATABASE IF NOT EXISTS jbbserverstg CHARACTER SET utf8 COLLATE utf8_bin;


-- 备份mgt_users
DROP TABLE mgt_user_temp;
CREATE TABLE mgt_user_temp LIKE mgt_users; 
INSERT INTO mgt_user_temp SELECT * FROM mgt_users;

-- 创建mgt_channel_users
CREATE TABLE mgt_org_users
(
   user_id int not null comment '用户编号',
   org_id int NOT NULL  comment '组织id',
   creation_date timestamp(3) not null comment '创建时间',
   entry_status TINYINT  DEFAULT 0 comment '是否进件',
   entry_date timestamp DEFAULT CURRENT_TIMESTAMP comment '进件时间',
   s_channel_code varchar(20) not null comment '最初来源渠道编号',
   s_org_id int not null comment '最初来源组织',
   s_user_type int  DEFAULT 1 comment '最初来源用户类型',
   PRIMARY KEY (org_id, user_id)
)
ENGINE InnoDB;

-- move 用户注册渠道数据
insert into mgt_org_users (user_id, org_id, creation_date, entry_status, entry_date,  s_channel_code, s_org_id, s_user_type)
select 
	u.user_id, acc.org_id, u.creation_date, 
	p1.value as entry_status,
	p1.update_date as entry_date, 
	u.channel_code,
	acc.org_id,
	1
from mgt_users u
LEFT JOIN mgt_user_properties p1 on p1.user_id = u.user_id AND p1.`name` = 'entry'
left join mgt_channels  c  on u.channel_code = c.channel_code
left join mgt_accounts acc on c.creator = acc.account_id
;

-- 移除mgt_users 表里的channel_code信息，并统一数据
DROP TABLE mgt_user_temp_02;
CREATE TABLE mgt_user_temp_02 LIKE mgt_users; 
INSERT INTO mgt_user_temp_02 
SELECT * 
FROM mgt_users u;
alter table mgt_users drop column channel_code;
alter table mgt_users drop column user_type;


-- mgt_users表 添加表字段
ALTER TABLE mgt_users add COLUMN ocr_idcard_verified tinyint not null default 0 comment '身份证ocr是否完成';
ALTER TABLE mgt_users add COLUMN video_verified tinyint not null default 0 comment '是否视频认证';
ALTER TABLE mgt_users add COLUMN idcard_verified tinyint not null default 0 comment '是否身份证认证';
ALTER TABLE mgt_users add COLUMN avatar_pic varchar(1000) comment '头像图片名';
ALTER TABLE mgt_users add COLUMN nick_name varchar(50) comment '昵称';
ALTER TABLE mgt_users add COLUMN password varchar(250) comment '密码';
ALTER TABLE mgt_users add COLUMN trade_password varchar(250) comment '交易密码';
ALTER TABLE mgt_users add COLUMN has_contacts tinyint not null default 0 comment '是否上传通讯录';


-- mgt_user_apply_records 表 添加字段
ALTER TABLE mgt_user_apply_records add COLUMN s_channel_code varchar(20) not null comment '申请来源渠道编号';
ALTER TABLE mgt_user_apply_records add COLUMN s_org_id int not null comment '申请来源组织ID';
ALTER TABLE mgt_user_apply_records add COLUMN s_user_type int not null comment '申请时用户类型';




--0716
ALTER TABLE mgt_channels ADD COLUMN check_wool tinyint NOT NULL DEFAULT 0 COMMENT '检测羊毛用户： 0-非羊毛，1-羊毛';
ALTER TABLE mgt_channels ADD COLUMN check_mobile tinyint NOT NULL  DEFAULT 0 COMMENT '检测空号： 0-非空号，1-空号';




-- 更新各申请项数据
--update mgt_user_apply_records  uar
--inner join mgt_org_users ou on ou.user_id = uar.user_id and ou.org_id = uar.org_id
--set uar.s_channel_code = ou.s_channel_code, uar.s_org_id = ou.s_org_id, uar.s_user_type = 1

--update mgt_user_apply_records uar
--inner join (
--select uar.apply_id, uar.user_id, uar.org_id, ou.s_channel_code, ou.org_id as ouOrgId,  ou.s_org_id as ouSOrgId
--from mgt_user_apply_records uar 
--left join mgt_org_users ou on ou.user_id = uar.user_id and ou.org_id = 1
--where uar.s_org_id = 0
--) t on t.apply_id  = uar.apply_id
--set uar.s_channel_code= t.s_channel_code , uar.s_org_id =1, uar.s_user_type =1

--insert into mgt_org_users
--(user_id, org_id, creation_date, s_channel_code, s_org_id, s_user_type, entry_status, entry_date)
--select distinct uar.user_id, uar.org_id, u.creation_date, uar.s_channel_code,  uar.s_org_id , 1 , up.value, up.update_date
--from mgt_user_apply_records uar
--left join mgt_org_users ou on ou.user_id = uar.user_id and ou.org_id = uar.org_id
--left join mgt_users u on u.user_id = uar.user_id
--left join mgt_user_properties up on up.user_id = uar.user_id and up.name = "entry"
--where ou.org_id is null






