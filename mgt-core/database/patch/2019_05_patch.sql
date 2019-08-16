-- 添加运营岗新角色 - 运营助理  role 110, 权限 119

INSERT INTO mgt_roles (role_id, description, application_id) VALUES (110, '运营助理', 1);
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (110, 119);

--添加贷超产品UV数，贷超导流漏斗表权限
INSERT INTO mgt_permissions(permission_id, description) VALUES (140, '贷超产品UV数');
INSERT INTO mgt_permissions(permission_id, description) VALUES (141, '贷超导流漏斗表');

INSERT INTO mgt_role_permissions(role_id, permission_id) VALUES (107, 140);
INSERT INTO mgt_role_permissions(role_id, permission_id) VALUES (107, 141);
INSERT INTO mgt_role_permissions(role_id, permission_id) VALUES (103, 141);
INSERT INTO mgt_role_permissions(role_id, permission_id) VALUES (104, 140);
INSERT INTO mgt_role_permissions(role_id, permission_id) VALUES (104, 141);
INSERT INTO mgt_role_permissions(role_id, permission_id) VALUES (105, 140);
INSERT INTO mgt_role_permissions(role_id, permission_id) VALUES (105, 141);
INSERT INTO mgt_role_permissions(role_id, permission_id) VALUES (106, 140);
INSERT INTO mgt_role_permissions(role_id, permission_id) VALUES (110, 140);


-- mgt_users表，添加第一次渠道信息
ALTER TABLE mgt_users ADD COLUMN  first_channel_code varchar(20) COMMENT '第一次进入时渠道信息';

alter table mgt_users add index i_mgt_users_fchannel_code (first_channel_code);


-- 2019-05-08 取消产品商务133权限
DELETE FROM mgt_role_permissions WHERE role_id=106 AND permission_id=133

-- 创建财务操作日志表
CREATE TABLE `mgt_fin_op_log`  (
  `log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志编号',
  `account_id` int(11) NULL DEFAULT NULL COMMENT '操作账户ID',
	`source_id`  varchar(128)  NULL DEFAULT NULL COMMENT '渠道或者平台id',
  `type` int(4) NOT NULL COMMENT '1渠道 2 平台',
  `op_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `params` varchar(500)  NULL DEFAULT NULL COMMENT '操作理由',
  PRIMARY KEY (`log_id`) USING BTREE,
  INDEX `i_mgt_account_op_log_accid`(`account_id`) USING BTREE,
  INDEX `i_mgt_account_op_log_sourceId`(`source_id`) USING BTREE,
  INDEX `i_mgt_account_op_log_optype`(`type`) USING BTREE,
  INDEX `i_mgt_account_op_log_opdate`(`op_date`) USING BTREE
) ;

-- 贷超产品 计算uv模式记录表
CREATE TABLE `mgt_platform_statistic`  (
  `statistic_date` date NOT NULL COMMENT '统计时间',
  `creation_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  PRIMARY KEY (`statistic_date`) USING BTREE
) ;

update mgt_info set db_version = 2 ;


-- 0515 添加索引 
alter table mgt_loan_platforms add index i_mgt_loan_platforms_sales (sales_id);

ALTER TABLE `mgt_channel_pv` ADD INDEX `i_mgt_channel_pv_channelcode` (`channel_code`);
ALTER TABLE `mgt_channel_pv` ADD INDEX `i_mgt_channel_pv_sdate` (`statistic_date`);
ALTER TABLE `mgt_channel_pv` ADD INDEX `i_mgt_channel_pv_uv` (`uv`);

ALTER TABLE `mgt_fin_channel_statistic_daily` ADD INDEX `i_mgt_fin_channel_statistic_daily_ccode` (`channel_code`);

update mgt_info set db_version = 3;


-- 0517
INSERT INTO mgt_permissions(permission_id, description) VALUES (142, '贷超产品收款和截图上报');
INSERT INTO mgt_role_permissions(role_id, permission_id) VALUES (101, 142);
INSERT INTO mgt_role_permissions(role_id, permission_id) VALUES (102, 142);
INSERT INTO mgt_role_permissions(role_id, permission_id) VALUES (104, 142);
INSERT INTO mgt_role_permissions(role_id, permission_id) VALUES (105, 142);
INSERT INTO mgt_role_permissions(role_id, permission_id) VALUES (106, 142);
INSERT INTO mgt_role_permissions(role_id, permission_id) VALUES (107, 142);

update mgt_info set db_version = 4;

--0528

INSERT INTO mgt_roles (role_id, description,application_id) VALUES (111, '应用市场运营',1);
INSERT INTO mgt_permissions (permission_id, description) VALUES (144, 'APP注册分析');
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (101, 144);
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (102, 144);
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (104, 144);
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (107, 144);
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (111, 144);

update mgt_info set db_version = 5;

-- 添加权限
---关联sql  ip_statistics.sql
INSERT INTO mgt_permissions (permission_id, description) VALUES (143, '贷超产品IP异常');
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (101, 143);
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (102, 143);
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (104, 143);
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (107, 143);

update mgt_info set db_version = 6;
-- 0527
CREATE TABLE `mgt_channel_funnel_filter` (
	filter_id INT ( 11 ) NOT NULL AUTO_INCREMENT COMMENT '规则ID',
	filter_name  VARCHAR ( 20 ) NOT NULL COMMENT '规则名称',
	style VARCHAR ( 100 ) NULL DEFAULT NULL COMMENT '显示样式',
	`index` INT ( 11 ) NULL DEFAULT NULL COMMENT '优先级',
	creation_date TIMESTAMP ( 3 ) NOT NULL DEFAULT CURRENT_TIMESTAMP ( 3 ) ON UPDATE CURRENT_TIMESTAMP ( 3 ),
	PRIMARY KEY ( `filter_id` )
);

-- 0528
CREATE TABLE `mgt_channel_funnel_condition` (
	condition_id INT ( 11 ) NOT NULL AUTO_INCREMENT COMMENT '条件ID',
	filter_id INT ( 11 ) NOT NULL  COMMENT '规则ID',
	property VARCHAR ( 20 ) NOT NULL COMMENT '条件属性',
	operator VARCHAR ( 20 ) NOT NULL COMMENT '条件运算符',
	value  VARCHAR ( 20 ) NOT NULL COMMENT '条件值',
	creation_date TIMESTAMP ( 3 ) NOT NULL DEFAULT CURRENT_TIMESTAMP ( 3 ) ON UPDATE CURRENT_TIMESTAMP ( 3 ),
	PRIMARY KEY ( `condition_id` )
);

-- 0530添加权限
INSERT INTO mgt_permissions (permission_id, description) VALUES (145, '贷超导流漏斗表高亮配置');
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (101, 145);
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (102, 145);
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (104, 145);
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (107, 145);

update mgt_info set db_version = 7;

-- 0604 ip统计表
CREATE TABLE mgt_ip_statistics  (
  statistic_date date NOT NULL COMMENT '统计时间',
  ip_address varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'ip地址 ',
  uv_cnt int(11) NULL DEFAULT 0 COMMENT 'UV',
  register_cnt int(11) NULL DEFAULT 0 COMMENT '注册数',
  download_click_cnt int(11) NULL DEFAULT 0 COMMENT '下载点击',
  app_login_cnt int(11) NULL DEFAULT 0 COMMENT '登录数',
  p_uv_cnt int(11) NULL DEFAULT 0 COMMENT '贷超uv',
  p_click_cnt int(11) NULL DEFAULT 0 COMMENT '贷超pv',
  channel_sale varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道和商务',
  PRIMARY KEY (statistic_date, ip_address) USING BTREE
);

ALTER TABLE `mgt_ip_statistics` ADD INDEX `i_mgt_ip_statistics_csales` (`channel_sale`);

update mgt_info set db_version = 8;

-- 0604
ALTER TABLE mgt_users ADD COLUMN ip_address_first VARCHAR(20) DEFAULT NULL COMMENT '四位ip地址';
ALTER TABLE mgt_users ADD INDEX i_user_ip_address_first (ip_address_first);
ALTER TABLE mgt_user_event_logs  ADD COLUMN remote_address_first  VARCHAR(20) DEFAULT NULL COMMENT '四位ip地址';
ALTER TABLE mgt_user_event_logs ADD INDEX i_user_remote_address_first (remote_address_first);

update mgt_users set ip_address_first = substring_index(ip_address ,',',1) where ip_address_first is null ;
update mgt_user_event_logs set remote_address_first = substring_index(remote_address ,',',1) where remote_address is null;

alter table mgt_users modify column ip_address varchar(64) ;
alter table mgt_user_event_logs modify column remote_address varchar(64) ;

update mgt_info set db_version = 9;


