-- 1008 --小金条消息记录表
CREATE TABLE mgt_xjl_message_detail (
	msg_id VARCHAR (30) NOT NULL COMMENT '消息ID',
	apply_id INT NOT NULL COMMENT '申请ID',
	account_id INT NOT NULL COMMENT '操作ID',
	mobile VARCHAR (20) NOT NULL COMMENT '手机号',
	msg_desc VARCHAR (200) DEFAULT NULL COMMENT '消息内容',
	`status` VARCHAR (20) DEFAULT NULL COMMENT '短信状态',
	status_desc VARCHAR (20) DEFAULT NULL COMMENT '状态说明',
	notify_date TIMESTAMP(3) NULL DEFAULT NULL COMMENT '回调时间',
	creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (msg_id)
)ENGINE InnoDB;



 --1008
 ALTER TABLE mgt_data_whtianbei_report add column mf_json mediumtext DEFAULT NULL COMMENT '米房借条数据';
 
 --1023
CREATE TABLE mgt_loan_platform_publish (
	id INT NOT NULL auto_increment COMMENT '自增，主键',
	platform_id INT NOT NULL COMMENT '平台ID',
	publish_date TIMESTAMP (3) NULL DEFAULT NULL COMMENT '发布时间',
	creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	is_deleted TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除,默认为0',
	PRIMARY KEY (id)
) ENGINE INNODB;

--1024
CREATE TABLE mgt_dunxin_msg_log
(
   order_id varchar(50),
   account_id int not null comment '账号id',
   phone_number  varchar(20) comment '手机号',
   send_status  varchar(10) comment '发送状态 0发送中 1发送失败,2发送成功',
   mode_id  varchar(10) comment '模板id',
   send_date Timestamp not null default CURRENT_TIMESTAMP() comment '创建时间',
   PRIMARY KEY (order_id)
)
ENGINE InnoDB;

--1024
CREATE TABLE `mgt_human_lp_phones` (
  `phone_number` varchar(20) NOT NULL COMMENT '手机号',
  `username` varchar(20) DEFAULT NULL COMMENT '名称',
  `sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `cdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '注册时间',
  `batch_id` varchar(20) NOT NULL COMMENT '批次时间',
  PRIMARY KEY (`phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--1025 请取消mgt_human_lp_phones表中cdate的 根据当前时间戳跟新,防止cdate随修改而跟新
ALTER TABLE mgt_human_lp_phones add column push_status tinyint DEFAULT 0 COMMENT '是否拉取数据';

-- 1025 在human 表中添加手机号后四位的数据作为查询索引
ALTER TABLE mgt_human_lp_phones add column last_phone varchar(10) COMMENT '手机号后四位';

--1026 请取消mgt_human_lp_phones表中creation_date的 根据当前时间戳跟新,防止creation_date随修改而跟新
ALTER TABLE mgt_human_lp_phones add column creation_date timestamp not null default CURRENT_TIMESTAMP() comment '创建时间';

--1026creation_date创建索引
alter table mgt_human_lp_phones add index i_mgt_human_lp_phones_creation_date (creation_date);

--1026last_phone创建索引
alter table mgt_human_lp_phones add index i_mgt_human_lp_phones_last_phone (last_phone);

--1027 在human 表中添加字段
ALTER TABLE mgt_human_lp_phones add column send_status varchar(10) COMMENT '发送状态';

--1027 修改表的字段名称
ALTER TABLE mgt_human_lp_phones change column send_status last_send_status varchar(30);

--1027 在human 表中添加字段
ALTER TABLE mgt_human_lp_phones add column last_send_date timestamp null COMMENT '上次发送时间';

--1027 mgt_channels表增加配置
ALTER TABLE mgt_channels add column merchant_id2 INT(11) COMMENT 'h5配置';
--1027 在mgt_dunxin_msg_log 表中添加字段
ALTER TABLE mgt_dunxin_msg_log add column error_msg varchar(20) null COMMENT '错误原因';

--1030

CREATE TABLE mgt_data_julixin_urls (
  user_id int(11) NOT NULL,
  report_type varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  h5_url varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  creation_date timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  h5_short_url varchar(30) DEFAULT NULL COMMENT '短链接',
  PRIMARY KEY (user_id,report_type)
) ;
--1030
CREATE TABLE mgt_data_julixin_report (
	token VARCHAR (100) NOT NULL COMMENT '报告查询凭证',
	user_id INT (11) DEFAULT NULL COMMENT '用户号',
	apply_id INT (11) DEFAULT NULL COMMENT '申请借款ID',
	report_type VARCHAR (32) NOT NULL COMMENT '报告类型。 jiedaibao - 借贷宝，mifang - 米房， wuyoujietiao- 无忧，jinjiedao -今借到',
	STATUS INT (11) NOT NULL DEFAULT '0' COMMENT '报告状态, 0 - 提交； 99-获取成功； 1 - 失败',
	creation_date TIMESTAMP (3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3) COMMENT '任务创建时间',
	PRIMARY KEY (`token`),
	KEY `i_mgt_data_julixin_report_uid` (`user_id`),
	KEY `i_mgt_data_julixin_report_report_type` (`report_type`),
	KEY `i_mgt_data_julixin_report_applyid` (`apply_id`)
)

--1106 mgt_channels表增加代理组织id字段
ALTER TABLE mgt_channels ADD COLUMN delegate_org_id INT(4) default NULL comment '代理组织id';
 --1016
 ALTER TABLE mgt_xjl_users_accounts ADD COLUMN loan_cnt INT default 0 comment '借款次数';
 
 --1106
 --添加索引
ALTER TABLE mgt_xjl_apply_records ADD INDEX  i_xjl_creation_date (creation_date);
ALTER TABLE mgt_xjl_apply_records ADD INDEX  i_xjl_loan_date (loan_date);
ALTER TABLE mgt_xjl_apply_records ADD INDEX  i_xjl_repayment_date (repayment_date);
ALTER TABLE mgt_xjl_apply_records ADD INDEX  i_xjl_final_acc_id (final_acc_id);
ALTER TABLE mgt_xjl_apply_records ADD INDEX  i_xjl_collector_acc_id (collector_acc_id); 

ALTER TABLE mgt_xjl_pay_records ADD INDEX  i_xjl_pay_creation_date (creation_date);
ALTER TABLE mgt_xjl_pay_records ADD INDEX  i_xjl_pay_apply_id (applyId);

ALTER TABLE mgt_xjl_users_accounts ADD INDEX  i_xjl_users_accounts_id (account_id);

ALTER TABLE mgt_xjl_user_orders ADD INDEX  i_xjl_users_orders_creation_date (creation_date);
ALTER TABLE mgt_xjl_user_orders ADD INDEX  i_xjl_users_orders_apply_id(apply_id);

--添加小金条apply的字段
--1109
ALTER TABLE mgt_xjl_apply_records ADD COLUMN apply_cnt INT default 1 comment '申请次数，默认为1。 用户每次提交申请时，递增';
ALTER TABLE mgt_xjl_apply_records ADD COLUMN loan_cnt INT default null comment '下款次数，默认为0，每次下款时，递增';
ALTER TABLE mgt_xjl_apply_records ADD COLUMN final_acc_id INT default null comment '审核员';
ALTER TABLE mgt_xjl_apply_records ADD COLUMN final_entry_date timestamp(3) null default null comment '审核员获取订单时间';
ALTER TABLE mgt_xjl_apply_records ADD COLUMN final_approval_date timestamp(3) null default null comment '审核通过订单时间';
ALTER TABLE mgt_xjl_apply_records ADD COLUMN collector_acc_id INT default null comment '催告员ID';
ALTER TABLE mgt_xjl_apply_records ADD COLUMN collector_entry_date timestamp(3) null default null comment '催告员领取时间';

--1106
 ALTER TABLE mgt_loan_platforms add column group_name VARCHAR(25) DEFAULT NULL COMMENT '分组名称';
 ALTER TABLE mgt_loan_platforms add column type int DEFAULT 1 COMMENT '类型 1 - 贷超,2 - H5, 3-社群贷超';
