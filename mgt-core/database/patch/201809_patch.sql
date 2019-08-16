
-- 0918
ALTER TABLE mgt_loan_platforms ADD COLUMN cpa_price INT default 0 comment '渠道单价';


-- 0919
-- 用户登录日志表
/*==============================================================*/
/* Table: mgt_user_login_logs     用户登录日志表               */
/*==============================================================*/
CREATE TABLE mgt_user_login_logs
(
   user_id int not null comment '用户ID',
   channel_code varchar(20) comment '渠道号',
   application_id int not null  default 0 comment 'applicationId, 0 默认，1， 补充资料， 2小金条',
   platform varchar(20) comment '平台：android or ios or web',
   user_type int comment '用户类型，H5版本相关，1 进件， 2 注册， 3 代理',
   remote_address varchar(50) comment 'IP地址',
   creation_date Timestamp(3) comment '登录时间'
)
ENGINE InnoDB;

alter table mgt_user_login_logs add index i_mgt_ulog_uid (user_id);
alter table mgt_user_login_logs add index i_mgt_ulog_ccode (channel_code);
alter table mgt_user_login_logs add index i_mgt_ulog_appid (application_id);
alter table mgt_user_login_logs add index i_mgt_ulog_platform (platform);
alter table mgt_user_login_logs add index i_mgt_ulog_utype (user_type);
alter table mgt_user_login_logs add index i_mgt_ulog_cdate (creation_date);


-- 用户组织APP渠道表
/*==============================================================*/
/* Table: mgt_org_app_channel_users     用户组织-APP-用户渠道结算表              */
/* 注意：程序逻辑，每个用户当天只能从一个app进入组织， 如果有通过两个不同的app进入，则后续的app要记is_hidden为true， 对渠道不可见             */
/*==============================================================*/
CREATE TABLE mgt_org_app_channel_users
(
   org_id int not null comment '组织ID',
   application_id int not null  default 0 comment 'applicationId, 0 默认，1， 补充资料， 2小金条',
   user_id int not null comment '用户ID',
   channel_code varchar(20) comment '渠道号',
   user_type int comment '用户类型，H5版本相关，1 进件， 2 注册， 3 代理, 商家用户统一为进件 1',
   is_hidden tinyint  default 0 comment '是否隐藏，对渠道不可见',
   creation_date Timestamp not null default CURRENT_TIMESTAMP() comment '创建时间',
   PRIMARY KEY (org_id, application_id, user_id)
)
ENGINE InnoDB;
alter table mgt_org_app_channel_users add index i_mgt_oausers_oid (org_id);
alter table mgt_org_app_channel_users add index i_mgt_oausers_uid (user_id);
alter table mgt_org_app_channel_users add index i_mgt_oausers_appid (application_id);
alter table mgt_org_app_channel_users add index i_mgt_oausers_ccode (channel_code);
alter table mgt_org_app_channel_users add index i_mgt_oausers_utype (user_type);
alter table mgt_org_app_channel_users add index i_mgt_ulog_ishidden (is_hidden);
alter table mgt_org_app_channel_users add index i_mgt_ulog_cdate (creation_date);


-- 0920 添加索引 
alter table mgt_org_users add index i_mgt_ousers_oid (org_id);
alter table mgt_org_users add index i_mgt_ousers_uid (user_id);

--0925
CREATE TABLE mgt_xjl_users_accounts
(
   org_id int not null comment '组织ID',
   user_id int not null comment '用户ID',
   account_id int not null COMMENT '客服账号ID',
   creation_date Timestamp not null default CURRENT_TIMESTAMP() comment '创建时间',
   PRIMARY KEY (org_id,user_id)
)

-- 0925 角色权限 调整

ALTER TABLE mgt_roles ADD COLUMN application_id INT default 0 comment '应用 - 0 - 风控，1-运营子系统，2 - 小金条';


--0926
ALTER TABLE mgt_xjl_users_accounts ADD COLUMN  receive_date timestamp  NULL  comment '领取时间';
ALTER TABLE mgt_xjl_users_accounts ADD COLUMN  user_status INT DEFAULT 0  comment '用户状态';

ALTER TABLE mgt_xjl_users_accounts MODIFY COLUMN  account_id  INT DEFAULT NULL  comment '客服ID';

-- 0926
ALTER TABLE mgt_organizations ADD COLUMN delegate_h5_type tinyint not null default 2 COMMENT '1 为进件，2为注册';

-- 优化sql 
 ALTER TABLE mgt_user_loan_records ADD INDEX  i_mgt_ulr_uid (`user_id`);
 ALTER TABLE mgt_user_apply_records ADD INDEX i_mgt_uar_initaccid_orgid_sorgid_stype_cdate (`init_acc_id`, `org_id`, `s_org_id`, `s_user_type`, `creation_date`);

 
 ALTER TABLE mgt_user_apply_records ADD INDEX i_mgt_uar_initaccid_orgid_sorgid_susertype_creationdate` (`init_acc_id`, `org_id`, `s_org_id`, `s_user_type`, `creation_date`);

