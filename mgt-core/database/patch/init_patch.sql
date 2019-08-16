-- 0426
ALTER TABLE mgt_data_td_preloan_report ADD COLUMN apply_id int comment '申请借款ID';

ALTER TABLE mgt_accounts ADD COLUMN is_deleted tinyint default 0 comment '是否删除';


ALTER TABLE mgt_channels ADD COLUMN  receive_mode int comment '进件模式：1 注册；2 进件';

ALTER TABLE mgt_data_club_preloan_report ADD COLUMN  user_id int comment '用户ID';
alter table mgt_data_club_preloan_report add index i_mgt_clubreport_uid (user_id);


-- 0428
ALTER TABLE mgt_account_login_log ADD COLUMN  province varchar(30) comment '省份';
ALTER TABLE mgt_account_login_log ADD COLUMN  city varchar(30) comment '城市';
ALTER TABLE mgt_account_login_log ADD COLUMN  detail varchar(500) comment '接口返回详情';

-- 0429
ALTER TABLE mgt_account_dflow_settings ADD COLUMN  is_closed tinyint not null default 0 comment '开关：0关闭；1开启';
ALTER TABLE mgt_account_op_log MODIFY COLUMN op_date timestamp;

-- 0430
ALTER TABLE mgt_user_apply_records CHANGE load_date loan_date timestamp default CURRENT_TIMESTAMP() comment '打款时间';


-- 0502
-- 创建上下游依赖表
CREATE TABLE mgt_account_dependencies
(
   account_id int not null comment '账户',
   dep_account_id int not null comment '依赖账户',
   dep_relation int not null default 1 comment '依赖关系。 1 - 上一级， 2 - 下一级'
)
ENGINE InnoDB;
alter table mgt_account_dependencies add index i_mgt_acc_deps_accid (account_id);
alter table mgt_account_dependencies add index i_mgt_acc_deps_dep_accid (dep_account_id);
alter table mgt_account_dependencies add index i_mgt_acc_deps_dep_r (dep_relation);

-- 渠道表：针对 电销、中介添加字段。隐藏渠道在前端不显示
ALTER TABLE mgt_channels add column is_hidden tinyint not null default 0 comment '是否隐藏渠道： 0-不隐藏，1-隐藏';

-- 借款记录表
ALTER TABLE mgt_user_loan_records add column apply_id int comment '申请借款ID';
alter table mgt_user_loan_records add index i_mgt_loanr_apply_id (apply_id);



-- 充值模块表
/*==============================================================*/
/* Table: mgt_org_recharges     组织账户总表                */
/*==============================================================*/
CREATE TABLE mgt_org_recharges
(
   org_id int not null comment '组织ID',
   total_data_amount int not null default 0 comment '总充值金额',
   total_sms_amount int not null default 0 comment '总短信充值金额',
   total_data_expense int not null default 0 comment '总流量消费金额',
   total_sms_expense int not null default 0 comment '总短信消费金额',
   PRIMARY KEY (org_id)
)
ENGINE InnoDB;


-- 充值模块表
/*==============================================================*/
/* Table: mgt_org_recharge_detail     组织账户流水表                */
/*==============================================================*/
CREATE TABLE mgt_org_recharge_detail
(
   trade_no varchar(32) not null comment '交易号',
   account_id int not null comment '消耗人员accountId',
   op_type int not null comment '11 - 充值数据，12 - 充值流量， 21 - 消耗数据， 22-消耗短信',
   amount int not null default 0 comment '金额',
   status int not null default 0 comment '0 - 提交，1 - 记账成功',
   creation_date timestamp(3) comment '创建时间',
   PRIMARY KEY (trade_no)
)
ENGINE InnoDB;

-- 0503

ALTER TABLE mgt_user_apply_records add column creation_date timestamp not null default CURRENT_TIMESTAMP() comment '创建时间';
alter table mgt_user_apply_records modify final_date  timestamp null;
alter table mgt_user_apply_records modify loan_date  timestamp null;



-- 0503
/*==============================================================*/
/* Table: mgt_data_yx_report     亿象报告：借贷宝、金借到、米房、无忧   */
/*==============================================================*/
CREATE TABLE mgt_data_yx_report
(
   task_id varchar(32) not null comment '任务号',
   user_id int null comment '用户号',
   apply_id int comment '申请借款ID',
   report_type varchar(32) not null comment '报告类型。 jiedaibao - 借贷宝，mifang - 米房， wuyoujietiao- 无忧，jinjiedao -今借到',
   status int not null default 0 comment '报告状态, 0 - 提交； 99-获取成功； 1 - 失败',
   token varchar(100) not null comment '报告查询凭证',
   creation_date timestamp(3) comment '任务创建时间',
   PRIMARY KEY (task_id)
)
ENGINE InnoDB;
alter table mgt_data_yx_report add index i_mgt_data_yx_report_uid (user_id);
alter table mgt_data_yx_report add index i_mgt_data_yx_report_report_type (report_type);
alter table mgt_data_yx_report add index i_mgt_data_yx_report_applyid (apply_id);


--0504
/*用户mgt_users表  添加字段*/
ALTER TABLE mgt_users ADD COLUMN  idcard_rear varchar(50) comment '身份证正面';
ALTER TABLE mgt_users ADD COLUMN  idcard_back varchar(50) comment '身份证反面';
ALTER TABLE mgt_users ADD COLUMN  idcard_info varchar(50) comment '手持身份证';
ALTER TABLE mgt_users ADD COLUMN  contract1_relation varchar(10) comment '联系人1关系';
ALTER TABLE mgt_users ADD COLUMN  contract1_username varchar(20) comment '联系人1名字';
ALTER TABLE mgt_users ADD COLUMN  contract1_phonenumber varchar(20) comment '联系人1联系方式';
ALTER TABLE mgt_users ADD COLUMN  contract2_relation varchar(10) comment '联系人2关系';
ALTER TABLE mgt_users ADD COLUMN  contract2_username varchar(20) comment '联系人2名字';
ALTER TABLE mgt_users ADD COLUMN  contract2_phonenumber varchar(20) comment '联系人2联系方式';
ALTER TABLE mgt_users ADD COLUMN  vidoe_screen_shot varchar(50) comment '上传视频截图';

-- 0504
-- 手机号检测表
/*==============================================================*/
/* Table: mgt_mobiles     手机号检测表               */
/*==============================================================*/
CREATE TABLE mgt_mobiles
(
   id int not null auto_increment,
   phone_number varchar(20) comment '手机号',
   check_type varchar(10) comment '检测类型',
   check_result varchar(10) comment '检测结果',
   check_date timestamp(3) comment '检测时间',
   PRIMARY KEY (id)
)
ENGINE InnoDB;

-- 0505
-- 用户key表 保证用户安全
/*==============================================================*/
/* Table: mgt_user_keys      用户key表              */
/*==============================================================*/
CREATE TABLE mgt_user_keys
(
   user_key VARCHAR(250) NOT NULL COMMENT '账户API_KEY',
   user_id INT COMMENT '用户Id',
   application_id INT COMMENT '地址id',
   expiry TIMESTAMP(3) NOT NULL DEFAULT '2030-12-30 00:00:00' COMMENT '过期时间',
   is_deleted TINYINT NOT NULL DEFAULT 0,
   PRIMARY KEY (user_key)
)
ENGINE INNODB;
ALTER TABLE mgt_user_keys ADD CONSTRAINT fk_mgt_user_keys_userid FOREIGN KEY (user_id) REFERENCES mgt_users(user_id);
ALTER TABLE mgt_user_keys ADD INDEX fk_mgt_user_keys_key (user_key);


-- 0512
/*==============================================================*/
/* Table: mgt_message_code                                          */
/*==============================================================*/
CREATE TABLE mgt_message_code
(
   phone_number varchar(20) comment '手机号',
   channel_code varchar(20) comment '渠道号',
   msg_code varchar(20) comment '验证码',
   creation_date timestamp(3) not null comment '创建时间',
   expire_date timestamp(3) not null comment '失效时间',
   PRIMARY KEY (phone_number,channel_code)
)
ENGINE InnoDB;
alter table mgt_message_code add index i_mgt_message_code_code (msg_code);
alter table mgt_message_code add index i_mgt_message_code_cdate (creation_date);

-- 0511
/*==============================================================*/
/* Table: mgt_users                                          */
/*==============================================================*/
ALTER TABLE mgt_users ADD COLUMN  jingdong_verified tinyint(4) comment '京东认证';
ALTER TABLE mgt_users ADD COLUMN  si_verified tinyint(4) comment '社保认证';
ALTER TABLE mgt_users ADD COLUMN  gjj_verified tinyint(4) comment '公积金认证';
ALTER TABLE mgt_users ADD COLUMN  chsi_verified tinyint(4) comment '学信认证';

-- 0512
/*==============================================================*/
/* Table: mgt_areazones      地区表         		                    */
/*==============================================================*/
CREATE TABLE `mgt_areazones` (
  `province` int(11) NOT NULL,
  `city` int(11) NOT NULL,
  `zone` varchar(10) NOT NULL,
  `areazone` varchar(45) NOT NULL,
  PRIMARY KEY (`zone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 0512
/*==============================================================*/
/* Table: mgt_channel      渠道表         		                    */
/*==============================================================*/
ALTER TABLE mgt_channels add column taobao_required tinyint not null default 1 comment '淘宝验证';
ALTER TABLE mgt_channels add column jingdong_required tinyint not null default 1 comment '京东验证';
ALTER TABLE mgt_channels add column gjj_required tinyint not null default 1 comment '公积金验证';
ALTER TABLE mgt_channels add column sj_required tinyint not null default 1 comment '社保验证';
--0515
/*==============================================================*/
/* Table: mgt_account_dependencies        		                    */
/*==============================================================*/
DROP TABLE IF EXISTS `mgt_account_dependencies`;
CREATE TABLE `mgt_account_dependencies` (
  `account_id` int(11) NOT NULL COMMENT '账户',
  `dep_account_id` int(11) NOT NULL COMMENT '依赖账户',
  `dep_relation` int(11) NOT NULL DEFAULT '1' COMMENT '依赖关系。 1 - 上一级， 2 - 下一级',
  `role_id` int(11) NOT NULL,
  `index` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`index`),
  KEY `i_mgt_acc_deps_accid` (`account_id`),
  KEY `i_mgt_acc_deps_dep_accid` (`dep_account_id`),
  KEY `i_mgt_acc_deps_dep_r` (`dep_relation`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

--0516
ALTER TABLE mgt_accounts ADD COLUMN is_freeze tinyint default 0 comment '是否冻结';

--0518
/*==============================================================*/
/* Table: mgt_user_event_logs      用户访问记录表         		                    */
/*==============================================================*/
CREATE TABLE `mgt_user_event_logs` (
  `user_id` int(11) DEFAULT NULL COMMENT '用户Id',
  `source_id` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '渠道',
  `cookie_id` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '存储Id',
  `event_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '事件名称',
  `event_action` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '事件动作',
  `event_label` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '事作标签',
  `event_value` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '事件值',
  `creation_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `remote_address` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  KEY `i_event_logs_user` (`user_id`),
  KEY `i_event_logs_sid` (`source_id`),
  KEY `i_event_logs_cid` (`cookie_id`),
  KEY `i_event_logs_name` (`event_name`),
  KEY `i_event_logs_action` (`event_action`),
  KEY `i_event_logs_lable` (`event_label`),
  KEY `i_event_logs_value` (`event_value`),
  KEY `i_event_logs_cdate` (`creation_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;

--0518
ALTER TABLE mgt_organizations add column count int not null default 30 comment '账号数量';



-- 0519

ALTER TABLE mgt_users ADD COLUMN  jbb_user_id int comment 'JBB ID';
ALTER TABLE mgt_users ADD COLUMN  ip_area varchar(30) comment 'IP地区';


ALTER TABLE mgt_user_loan_record_details MODIFY COLUMN  loan_id int comment '借款编号 ';
ALTER TABLE mgt_user_loan_record_details MODIFY COLUMN  op_type int(4) comment '操作opType，对应日志表';
ALTER TABLE mgt_user_loan_record_details ADD COLUMN  amount_type int(4) comment '类型：1 打款；2 还入；3 展期费用(利息)';


/*==============================================================*/
/* Table: mgt_loan_record_op_log     借款记录操作日志表                  */
/*==============================================================*/
CREATE TABLE mgt_loan_record_op_log
(
   log_id int auto_increment comment '日志编号', 
   account_id int comment '操作账户ID',
   loan_id int comment '用户申请ID',
   op_type int(4) not null  comment '操作类型',
   op_date timestamp comment '操作时间',
   op_reason varchar(250) comment '操作理由',
   op_comment varchar(250) comment '操作记录',
   PRIMARY KEY (log_id)
)
ENGINE InnoDB;
alter table mgt_loan_record_op_log add index i_mgt_loan_record_op_log_accid (account_id);
alter table mgt_loan_record_op_log add index i_mgt_loan_record_op_log_loanid (loan_id);
alter table mgt_loan_record_op_log add index i_mgt_loan_record_op_log_optype (op_type);
alter table mgt_loan_record_op_log add index i_mgt_loan_record_op_log_opdate (op_date);


drop table mgt_user_loan_records;
CREATE TABLE mgt_user_loan_records
( 
   loan_id int auto_increment comment 'loanId', 
   iou_code varchar(50) comment '贷款记录编号，如果为借帮帮记录，则为借帮帮借条编号',
   apply_id int comment '申请借款ID',
   account_id int comment '出借人 ',
   user_id int comment '借款人',
   status int(4) not null default 1 comment '款项状态',
   iou_status int(4) not null default 0 comment '借条状态, JBB平台同步',
   iou_platform_id int DEFAULT 0 COMMENT '打借条平台：0其他;1 借帮帮；2借贷宝',
   borrowing_amount int not null comment '借款金额',
   annual_rate int DEFAULT NULL COMMENT '年化利率, 记录整数 ， 比如1.5%，记为150',
   borrowing_date timestamp null default null comment '借款时间',
   borrowing_days int comment '借款天数',
   repayment_date timestamp null default null comment '到期时间',
   loan_acc_id int comment '打款人员',
   loan_amount int comment '打款金额',
   loan_date timestamp null default null comment '打款时间',
   collector_acc_id int comment '催收人员',
   creation_date timestamp  null default null comment '创建时间',
   update_date timestamp null default null comment '更新时间',
   actual_repayment_date timestamp null default null comment '实际还款时间',
   PRIMARY KEY (loan_id)
)

--0519
/*==============================================================*/
/* Table: mgt_iou_platform      借条平台基表         		                    */
/*==============================================================*/
CREATE TABLE mgt_iou_platform
(
   iou_platform_id int not null auto_increment,
	 platform_name varchar(50) comment '借条平台名称',
   description varchar(50) comment '描述',
   PRIMARY KEY (iou_platform_id)
)
ENGINE InnoDB;

-- 0521
ALTER TABLE mgt_user_loan_records ADD COLUMN  repay_amount int comment '催回金额总计';

--0521 为渠道账号添加唯一索引
ALTER TABLE mgt_channels ADD UNIQUE INDEX(source_phone_number);

--0528 为渠道表增加学信网验证
ALTER TABLE mgt_channels add column chsi_required tinyint not null default 1 comment '学信网验证';

--0528 组织添加短信签名和短信模板字段
ALTER TABLE mgt_organizations ADD COLUMN  sms_sign_name varchar(20) comment '短信签名';
ALTER TABLE mgt_organizations ADD COLUMN  sms_template_id varchar(20) comment '短信模板';

--0529 添加拒绝时间和挂起时间
ALTER TABLE mgt_user_apply_records ADD COLUMN  reject_date timestamp NULL DEFAULT NULL COMMENT '拒绝时间';
ALTER TABLE mgt_user_apply_records ADD COLUMN  hangup_date timestamp NULL DEFAULT NULL COMMENT '挂起时间';

--0601
/*==============================================================*/
/* Table: mgt_data_yx_urls      二维码表         		                    */
/*==============================================================*/
DROP TABLE IF EXISTS `mgt_data_yx_urls`;
CREATE TABLE `mgt_data_yx_urls` (
  `user_id` int(11) NOT NULL,
  `report_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `h5_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `creation_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  PRIMARY KEY (`user_id`,`report_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 修改mgt_user表字段
alter table mgt_users modify column contract1_relation VARCHAR(20);
alter table mgt_users modify column contract2_relation VARCHAR(20);

--修改mgt_channels字段
alter table mgt_channels  alter column qq_required set default 0;
alter table mgt_channels  alter column wechat_required set default 0;
alter table mgt_channels  alter column zhima_required set default 0;
alter table mgt_channels  alter column idcard_info_required set default 0;
alter table mgt_channels  alter column idcard_back_required set default 0;
alter table mgt_channels  alter column idcard_rear_required set default 0;
alter table mgt_channels  alter column header_required set default 0;
alter table mgt_channels  alter column mobile_contract1_required set default 0;
alter table mgt_channels  alter column mobile_contract2_required set default 0;
alter table mgt_channels  alter column mobile_service_info_required set default 0;
alter table mgt_channels  alter column taobao_required set default 0;
alter table mgt_channels  alter column jingdong_required set default 0;
alter table mgt_channels  alter column gjj_required set default 0;
alter table mgt_channels  alter column sj_required set default 0;
alter table mgt_channels  alter column chsi_required set default 0;

ALTER TABLE mgt_users ADD COLUMN  vidoe_screen_shot varchar(50) comment '上传视频截图';

ALTER TABLE mgt_user_loan_records ADD COLUMN  extention_date timestamp NULL DEFAULT NULL COMMENT '展期时间';

-- 0605
ALTER TABLE mgt_users ADD COLUMN  platform varchar(20) comment '注册平台 web, android, ios';
ALTER TABLE mgt_users ADD COLUMN  mobile_manufacture varchar(50) comment '手机型号';
ALTER TABLE mgt_users ADD COLUMN  idcard_address varchar(200) comment '身份证地址';
ALTER TABLE mgt_users ADD COLUMN  race varchar(20) comment '民族';

-- 0605
ALTER TABLE mgt_payorders CHANGE COLUMN order_no out_trade_no varchar(200);
ALTER TABLE mgt_payorders ADD COLUMN  trade_no VARCHAR(200) comment '支付宝流水号';
ALTER TABLE mgt_payorders ADD COLUMN  goods_id VARCHAR(200) comment '商品id';

-- 0606
CREATE TABLE `mgt_system_properties` (
  `name` varchar(50) NOT NULL COMMENT '属性名',
  `value` varchar(5000) DEFAULT NULL COMMENT '属性值',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 0608
ALTER TABLE mgt_data_yx_report MODIFY COLUMN  task_id varchar(100) comment '任务编号';

--0608
-- 新建表mgt_user_apply_records_prc
-- DROP TABLE IF EXISTS `mgt_user_apply_records_spc`;
CREATE TABLE `mgt_user_apply_records_spc` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `account_id` int(11) NOT NULL DEFAULT '0' COMMENT '账号Id',
  `creation_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '创建时间'
)

--0609
--新建表mgt_organization_relations
DROP TABLE IF EXISTS `mgt_organization_relations`;
CREATE TABLE mgt_organization_relations (
	org_id INT NOT NULL COMMENT '组织ID',
	sub_org_id INT NOT NULL COMMENT '子组织ID',
	PRIMARY KEY (`org_id`,`sub_org_id`)
)

--0611
ALTER TABLE mgt_users ADD COLUMN  is_hidden tinyint not null default 0 comment '是否隐藏用户： 0-不隐藏，1-隐藏';

--0611
--新建表mgt_organization_lenders
DROP TABLE IF EXISTS `mgt_organization_lenders`;
CREATE TABLE mgt_organization_lenders (
	org_id INT NOT NULL COMMENT '组织ID',
	account_id INT NOT NULL COMMENT '出借人账户ID',
	price INT COMMENT '竞价价格',
	title VARCHAR(20) COMMENT '显示名称',
	description VARCHAR(100) COMMENT '描述',
	creation_date timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
	update_date timestamp(3) null default null comment '更新时间',
	price_date  timestamp(3) null default null comment '报价时间',
	PRIMARY KEY (`org_id`)
)

--0612
ALTER TABLE mgt_organizations add column level int not null default 0 comment ' 1 有创建二级组织的权限，2 为二级组织， 0 为正常组织';

--0615
--新建表mgt_data_whtianbei_report
CREATE TABLE mgt_data_whtianbei_report
(
   report_id int not null auto_increment comment '报告ID',
   user_id int not null  comment '用户ID',
   apply_id int comment '申请ID',
   json_data MediumText not null  comment '返回查询结果',
   creation_date timestamp not null default CURRENT_TIMESTAMP() comment '创建时间',
   PRIMARY KEY (report_id)
)
ENGINE InnoDB auto_increment=100000;

--0620
ALTER TABLE mgt_data_yx_urls ADD COLUMN  h5_short_url varchar(30)  comment '短链接';

--0621
ALTER TABLE mgt_data_whtianbei_report add column blacklist_json mediumtext NOT NULL COMMENT '返回黑名单统计数据';

--0623
DROP TABLE IF EXISTS `mgt_user_properties`;
CREATE TABLE `mgt_user_properties` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `name` varchar(50) NOT NULL COMMENT '属性名',
  `value` varchar(500) DEFAULT NULL COMMENT '属性值',
  `is_hidden` tinyint(4) DEFAULT NULL COMMENT '是否隐藏',
  `update_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  PRIMARY KEY (`user_id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--0628
ALTER TABLE mgt_users ADD COLUMN  user_type VARCHAR(2) not null default 1 comment '用户类型： 1为进件, 2为注册';

-- 0630
ALTER TABLE mgt_user_contants CHANGE COLUMN user_id jbb_user_id int ;

--0629
ALTER TABLE mgt_organizations ADD COLUMN org_type int NOT NULL default 1 comment '组织类型';
ALTER TABLE mgt_organizations add COLUMN cnt int not null default 0 comment '导流数量';
ALTER TABLE mgt_organizations add COLUMN weight int not null default 0 comment '导流权重';
ALTER TABLE mgt_organizations add COLUMN data_enabled TINYINT not null default 0 comment '是否开启导流';
ALTER TABLE mgt_organizations add COLUMN filter_script VARCHAR(2000) comment '过滤jexl表达式';

--0703
ALTER TABLE mgt_user_contants ADD COLUMN  update_date timestamp NULL DEFAULT NULL COMMENT '更新时间';

--0705
ALTER TABLE mgt_data_whtianbei_report add column org_id INT COMMENT '组织Id';
--0706
alter table areazones add index i_areazones_zone (zone);
alter table mgt_user_apply_records_spc add primary key(user_id,account_id);
alter table mgt_user_apply_records_spc add index i_mgt_user_apply_records_spc_creationdate (creation_date);

--0706 mgt_data_whtianbei_report
alter table mgt_data_whtianbei_report drop primary key;
alter table mgt_data_whtianbei_report add primary key(`user_id`,`org_id`);

--0709
ALTER TABLE mgt_organizations ADD COLUMN company_name VARCHAR(100) comment '公司名称';

--0712
-- DROP TABLE IF EXISTS `mgt_organization_assign_settings`;
CREATE TABLE `mgt_organization_assign_settings` (
  `org_id` int(11) NOT NULL,
  `assign_type` int(11) NOT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `accounts` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`org_id`,`assign_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 0713
ALTER TABLE mgt_users ADD KEY index_idcard_6 (idcard(6));

-- 0718
-- 为市场员 增加帮帮导流权限
insert into mgt_role_permissions(role_id, permission_id) values(4, 9);

--0719
ALTER TABLE mgt_channels ADD COLUMN is_delegate tinyint NOT NULL  DEFAULT 0 COMMENT '委托链接： 0-非委托，1-委托';

--0720
ALTER TABLE mgt_channels ADD COLUMN redirect_url varchar(200) COMMENT '重定向链接地址';

--0721 创建表mgt_h5_merchants   请在创建表后,取消creation_date的 根据当前时间戳跟新,防止creation_date随修改而跟新
CREATE TABLE `mgt_h5_merchants` (
  `merchant_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商家 ID',
  `name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `short_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '短名称',
  `url` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '商家url链接',
  `logo` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商家logo地址',
  `desc1` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '描述1',
  `desc2` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '描述2',
  `desc3` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '描述3',
  `creator` int(11) DEFAULT NULL COMMENT '创建账号ID',
  `creation_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  PRIMARY KEY (`merchant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


--0723 创建表mgt_channel_account_info
CREATE TABLE `mgt_channel_account_info` (
  `channel_code` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '渠道编号',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '银行户名',
  `number` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '银行卡号',
  `bank_info` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '开户行',
  `update_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_account_id` int(11) DEFAULT NULL COMMENT '更新账号ID',
  PRIMARY KEY (`channel_code`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

ALTER TABLE mgt_users add column zhima_min INT COMMENT 'zhima分区间的最小值';
ALTER TABLE mgt_users add column zhima_max INT COMMENT 'zhima分区间的最大值';

--0723
CREATE TABLE mgt_fin_merchant_statistic_daily (
	statistic_date DATE NOT NULL COMMENT '统计时间',
	merchant_id INT NOT NULL COMMENT 'H5商家ID ',
	cnt INT NOT NULL DEFAULT 0 COMMENT '统计数',
	price INT NOT NULL DEFAULT 0 COMMENT '单价， 单位为分',
	expense INT NOT NULL DEFAULT 0 COMMENT '消耗额',
	amount INT NOT NULL DEFAULT 0 COMMENT '收款额',
	balance INT NOT NULL DEFAULT 0 COMMENT '余额',
	`status` INT (4) NOT NULL DEFAULT 0 COMMENT '状态：0-待确认， 1- 已确认',
	creation_date TIMESTAMP (3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3) COMMENT '创建时间',
	update_date TIMESTAMP (3) NULL DEFAULT NULL COMMENT '修改时间',
	confirm_date TIMESTAMP (3) NULL DEFAULT NULL COMMENT '确认时间',
	confrim_account_id INT COMMENT '确认人',
	PRIMARY KEY (statistic_date,merchant_id)
) ENGINE INNODB;

--0724 mgt_channel表增加字段 merchant_id 商家id 默认为null
ALTER TABLE mgt_channels add column merchant_id INT DEFAULT NULL COMMENT '商家ID';
-- 修改mgt_fin_org_sales_relation表的联合主键ogr_id,account_id 为单一主键 org_id
alter table mgt_fin_org_sales_relation drop primary key;
alter table mgt_fin_org_sales_relation add primary key(org_id);

--0726 mgt_user表添加索引
alter table mgt_users add index i_mgt_u_cdata (creation_date);

--0726
CREATE TABLE mgt_user_apply_feedback (
	apply_id INT (11) NOT NULL COMMENT '申请ID',
	reason VARCHAR (50) COMMENT '理由',
	reason_desc VARCHAR (50) COMMENT '理由',
	point INT (11) COMMENT '评分',
	zj_flag TINYINT default 0 COMMENT '疑是中价标记',
	PRIMARY KEY (apply_id)
) ENGINE = INNODB DEFAULT CHARSET = utf8;


--0730 创建表mgt_org_dflow_base组织进件导流特殊配置表
CREATE TABLE `mgt_org_dflow_base` (
  `org_id` int(11) NOT NULL COMMENT '组织ID',
  `dflow_id` int(11) NOT NULL COMMENT '导流配置ID',
  `update_account_id` int(11) DEFAULT NULL COMMENT '更新账户ID',
  `update_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  PRIMARY KEY (`org_id`,`dflow_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--0731
CREATE TABLE mgt_fin_channel_statistic_daily (
  statistic_date date NOT NULL COMMENT '统计时间',
  channel_code varchar(20) COLLATE utf8_bin NOT NULL COMMENT '渠道编号',
  cnt int(11) NOT NULL DEFAULT '0' COMMENT '统计数',
  price int(11) NOT NULL DEFAULT '0' COMMENT '单价',
  expense int(11) NOT NULL DEFAULT '0' COMMENT '消耗额',
  amount int(11) NOT NULL DEFAULT '0' COMMENT '收款额',
  balance int(11) NOT NULL DEFAULT '0' COMMENT '余额',
  status int(4) NOT NULL DEFAULT '0' COMMENT '状态：0-未支付， 1- 已支付',
  creation_date timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  update_date timestamp(3) NULL DEFAULT NULL COMMENT '更新时间',
  confirm_date timestamp(3) NULL DEFAULT NULL COMMENT '确认时间',
  confrim_account_id int(11) DEFAULT NULL COMMENT '确认人',
  PRIMARY KEY (statistic_date,channel_code)
)

--0731
ALTER TABLE mgt_fin_merchant_statistic_daily ADD COLUMN uv_cnt INT NOT NULL DEFAULT 0 COMMENT 'UV数';
ALTER TABLE mgt_fin_merchant_statistic_daily ADD COLUMN total_register_cnt INT NOT NULL DEFAULT 0 COMMENT '注册总数';


--0731
CREATE TABLE mgt_statistic_channel_daily (
  statistic_date date NOT NULL COMMENT '统计时间',
  channel_code varchar(20) NOT NULL COMMENT '渠道编号',
	user_type int(11) COMMENT ' 类型 1. 进件,2 注册',
  click_cnt int(11)  DEFAULT '0' COMMENT '点击数',
  uv_cnt int(11)  DEFAULT '0' COMMENT 'uv数',
  total_register_cnt int(11)  DEFAULT '0' COMMENT '总注册数',
  hidden_register_cnt int(11)  DEFAULT '0' COMMENT '隐藏注册数',
  entry_cnt int(11)  DEFAULT '0' COMMENT '进件数',
  hidden_entry_cnt int(11)  DEFAULT '0' COMMENT '隐藏进件数',
  sell_entry_cnt int(11)  DEFAULT '0' COMMENT '可卖进件数',
  hidden_sell_entry_cnt int(11)  DEFAULT '0' COMMENT '隐藏可卖进件数',
  h5_click_cnt int(11)  DEFAULT '0' COMMENT 'H5一键提交点击数',
  success_page_cnt int(11)  DEFAULT '0' COMMENT '成功页面数',
  download_click_cnt int(11) DEFAULT '0' COMMENT '下载点击数',
  level_1_cnt int(11)  DEFAULT '0' COMMENT '一档数',
  level_2_cnt int(11)  DEFAULT '0' COMMENT '二档数',
  zhima_1_cnt int(11)  DEFAULT '0' COMMENT '芝麻580以上数',
  age_1_cnt int(11)  DEFAULT '0' COMMENT '23-40岁数',
  PRIMARY KEY (statistic_date,channel_code,user_type)
)

-- 2018-08-01
-- 借帮帮销售角色
insert into mgt_roles (role_id, description) values (12, '销售员');
insert into mgt_permissions (permission_id, description) values (18, '销售');
insert into mgt_role_permissions(role_id, permission_id) values(12, 18);

-- 借帮帮结算角色
insert into mgt_roles (role_id, description) values (13, '结算员');
insert into mgt_permissions (permission_id, description) values (19, '结算');
insert into mgt_role_permissions(role_id, permission_id) values(13, 19);

-- 创建 渠道质量反馈表 相关表
CREATE TABLE mgt_statistic_feedback_daily (
  statistic_date date NOT NULL COMMENT '统计日期',
  channel_code varchar(20) COLLATE utf8_bin NOT NULL COMMENT '渠道编号',
  user_type int(4) NOT NULL COMMENT '模式',
  total_recommand_cnt int(11) DEFAULT NULL COMMENT '总推荐数',
  reject_cnt int(11) DEFAULT NULL COMMENT '拒绝数',
  hangup_cnt int(11) DEFAULT NULL COMMENT '挂起数',
  PRIMARY KEY (statistic_date,channel_code,user_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE mgt_statistic_feedback_detail_daily (
  statistic_date date NOT NULL COMMENT '统计日期',
  channel_code varchar(20) COLLATE utf8_bin NOT NULL COMMENT '渠道编号',
  user_type int(4) NOT NULL COMMENT '模式',
  reason varchar(20) COLLATE utf8_bin NOT NULL COMMENT '反馈原因',
  cnt int(4) DEFAULT NULL COMMENT '反馈数',
  PRIMARY KEY (statistic_date,channel_code,user_type,reason)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



-- 0809
ALTER TABLE mgt_org_recharge_detail ADD KEY index_ord_acc_id (account_id);
ALTER TABLE mgt_org_recharge_detail ADD KEY index_ord_op_type (op_type);
ALTER TABLE mgt_org_recharge_detail ADD KEY index_ord_status (status);
ALTER TABLE mgt_org_recharge_detail ADD KEY index_ord_cdate (creation_date);

--0810
ALTER TABLE mgt_user_apply_records ADD COLUMN  is_hidden tinyint not null default 0 comment '是否隐藏';

--0814
ALTER TABLE mgt_user_apply_records ADD COLUMN flag int not null default 0 comment '标记 1 - 特殊推荐';
ALTER TABLE mgt_user_apply_records ADD KEY index_uar_flag (flag);

--0815  修改mgt_channel_account_info表中number字段的长度为50
ALTER TABLE mgt_channel_account_info MODIFY COLUMN number varchar(50);

--0816 新建表mgt_user_addresses
CREATE TABLE mgt_user_addresses (
  address_id int(11) NOT NULL COMMENT '地址编号',
  type int(4) DEFAULT NULL COMMENT '类型 0-住址，1-工作地址',
  user_id int(11) DEFAULT NULL COMMENT '用户ID',
  province varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '省',
  city varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '市',
  area varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '区县',
  address varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  is_deleted int(4) DEFAULT NULL COMMENT '是否删除',
  creation_date timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  PRIMARY KEY (address_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--0820 小金条协议表
CREATE TABLE mgt_xjl_agreements (
	id INT NOT NULL COMMENT '协议编号',
	`name` VARCHAR (20) NOT NULL COMMENT '协议名字',
	`desc` VARCHAR (50) COMMENT '详细信息',
	version VARCHAR (10) COMMENT '版本',
	is_required TINYINT NOT NULL DEFAULT 0 COMMENT '是否必须',
	creation_date TIMESTAMP (3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建时间',
	PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

--0820 新建表mgt_xjl_apply_records 请删除creation_date的根据时间戳跟新
CREATE TABLE mgt_xjl_apply_records (
  apply_id int(11) NOT NULL AUTO_INCREMENT COMMENT '申请ID',
  user_id int(11) DEFAULT NULL COMMENT '用户Id',
  org_id int(11) DEFAULT NULL COMMENT '申请组织ID',
  status int(4) DEFAULT NULL COMMENT '状态',
  amount int(11) DEFAULT NULL COMMENT '借款额',
  service_fee int(11) DEFAULT NULL COMMENT '息费',
  creation_date timestamp(3) not null DEFAULT CURRENT_TIMESTAMP(3) COMMENT '提交申请时间',
  purpose varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '借款用途',
  loan_date timestamp NULL DEFAULT NULL COMMENT '放款日期',
  days int(11) DEFAULT NULL COMMENT '借款天数',
  repayment_date timestamp NULL DEFAULT NULL COMMENT '应还款日期',
  actual_repayment_date timestamp NULL DEFAULT NULL COMMENT '实际还款日期',
  PRIMARY KEY (apply_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE mgt_xjl_apply_record_op_logs (
  op_id int(11) NOT NULL AUTO_INCREMENT COMMENT '操作ID',
  apply_id int(11) NOT NULL COMMENT '操作ID',
  op_type int(11) NOT NULL COMMENT '操作类型',
  op_date timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '操作时间',
  account_id int(11) NOT NULL COMMENT '操作人员ID',
  comment varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (op_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--0822创建表用户信息设备信息表 mgt_user_devices
CREATE TABLE mgt_user_devices (
  user_Id int(11) NOT NULL COMMENT '用户ID',
  uuid varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'uuid',
  model varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '型号',
  platform varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '平台',
  version varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '版本',
  manufacturer varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '厂商',
  is_virtual tinyint(4) DEFAULT NULL COMMENT '是否虚拟设备',
  serial varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '硬件序列号',
  upate_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间 , 随数据更新变动',
  PRIMARY KEY (user_Id,uuid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--0822 日志表 
CREATE TABLE mgt_user_agree_logs (
	id INT (11) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
	apply_id INT COMMENT '用户申请ID',
	agreement_id INT COMMENT '协议ID',
	creation_date TIMESTAMP (3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3) COMMENT '同意日期',
	PRIMARY KEY (id)
) ENGINE = INNODB AUTO_INCREMENT = 1;

--0821 位置信息 新建表 mgt_user_locations
CREATE TABLE mgt_user_locations (
	location_id INT not null auto_increment COMMENT 'location ID',
	user_id INT COMMENT '用户ID',
    latitude double(6,3) COMMENT '纬度',
	longitude double(6,3) COMMENT '经度',
	accuracy double(6,3) COMMENT '精确度',
	altitude double(6,3) COMMENT '海拔',
	altitudeAccuracy double(6,3) COMMENT '海拔精确度',
	heading double(6,3) COMMENT '方向',
	speed double(6,3) COMMENT '速度',
	address varchar(100) COMMENT '地址',
	creation_date timestamp(3) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (location_id)
)

--修改 mgt_user_address表的主键自增
alter table mgt_user_addresses change address_id  address_id int(11) AUTO_INCREMENT;

--0824
/*用户mgt_users表  添加字段*/
ALTER TABLE mgt_users ADD COLUMN  xjl_basic_info_verified tinyint DEFAULT 0 comment '小金条用户基本资料是否完善';
ALTER TABLE mgt_users ADD COLUMN  contract1_xjl_relation varchar(20) comment '联系人1关系';
ALTER TABLE mgt_users ADD COLUMN  contract1_xjl_username varchar(20) comment '联系人1名字';
ALTER TABLE mgt_users ADD COLUMN  contract1_xjl_phonenumber varchar(20) comment '联系人1联系方式';
ALTER TABLE mgt_users ADD COLUMN  contract2_xjl_relation varchar(20) comment '联系人2关系';
ALTER TABLE mgt_users ADD COLUMN  contract2_xjl_username varchar(20) comment '联系人2名字';
ALTER TABLE mgt_users ADD COLUMN  contract2_xjl_phonenumber varchar(20) comment '联系人2联系方式';
ALTER TABLE mgt_users ADD COLUMN  marital_status INT comment '婚姻状态';

--0827 工作信息表
CREATE TABLE mgt_user_jobs (
	user_id INT NOT NULL COMMENT '用户ID',
	company VARCHAR (20) COMMENT '工作单位',
	address_id int  COMMENT '单位地址ID',
	occupation VARCHAR (10) COMMENT '职业',
	start_date date COMMENT '开始时间',
	end_date  date COMMENT '结束时间',
	creation_date TIMESTAMP (3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建时间',
	PRIMARY KEY (user_id)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

--0828 淘宝验证字段
ALTER TABLE mgt_users ADD COLUMN  taobao_verified tinyint(4) comment '淘宝认证';

--0828 视频验证
CREATE TABLE mgt_user_faceid_biz_no (
	user_id INT (10) NOT NULL,
	biz_no VARCHAR (255) NOT NULL,
	random_number VARCHAR (10) DEFAULT NULL,
	tokenrandomnumber VARCHAR (255) DEFAULT NULL,
	tokenvideo VARCHAR (255) DEFAULT NULL,
	random_data text,
	validatevideo_data text,
	verify_data text,
	PRIMARY KEY (user_id, biz_no)
)

--0828 剩余次数
CREATE TABLE mgt_user_product_count (
  user_id int(11) NOT NULL,
  product_name varchar(20) NOT NULL,
  count int(11) NOT NULL,
  PRIMARY KEY (user_id,product_name)
)

-- 0831 新增列
ALTER TABLE mgt_channels ADD COLUMN delegate_enabled  tinyint not null default 0 comment '标记 1 - 开启 0 - 关闭';


ALTER TABLE mgt_organizations ADD COLUMN delegate_enabled tinyint not null default 0 COMMENT '0 关闭,1 开启';
ALTER TABLE mgt_organizations ADD COLUMN delegate_weight int default 0 COMMENT '权重';

--0903
ALTER TABLE mgt_xjl_pay_records ADD COLUMN ret_reason varchar(250) COMMENT '订单失败或成功原因';

--0830 新建表mgt_loan_area_tags
CREATE TABLE mgt_loan_area_tags (
  area_tag_id int(11) NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  tag_name varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  area_id int(11) DEFAULT NULL COMMENT '页面区域',
  pos int(11) DEFAULT NULL COMMENT '位置, 在APP页面中的位置',
  logo_url varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT 'logo图标。在APP特定位置',
  creation_date timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建时间',
  PRIMARY KEY (area_tag_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--0830 mgt_loan_platforms
CREATE TABLE mgt_loan_platforms (
	platform_id INT auto_increment COMMENT 'ID,自增,PK',
	`name` VARCHAR (10) COMMENT '名称',
	short_name VARCHAR (10) COMMENT '短名称',
	url VARCHAR (200) COMMENT '链接地址',
	logo VARCHAR (200) COMMENT 'logo地址',
	ad_img_url VARCHAR (200) COMMENT '广告位图片地址',
	desc1 VARCHAR (20) COMMENT '描述1',
	desc2 VARCHAR (20) COMMENT '描述2',
	desc3 VARCHAR (20) COMMENT '描述3',
	min_amount INT DEFAULT 0 COMMENT '最小额度',
	max_amount INT DEFAULT 0 COMMENT '最大额度',
	loan_period VARCHAR (20) COMMENT '期限',
	interest_rate VARCHAR (20) COMMENT '贷款利率',
	approval_time INT COMMENT '审批时间，单位秒',
	is_new TINYINT COMMENT '是否最新口子',
	is_hot TINYINT COMMENT '是否热门口子',
	creator INT COMMENT '创建人ID',
	is_deleted TINYINT DEFAULT 0 COMMENT '是否删除,1为删除.默认为0',
	creation_time TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
	update_time TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY (platform_id)
)

--0830
CREATE TABLE mgt_loan_platform_tags (
	platform_id INT NOT NULL COMMENT 'platform ID,PK1',
	tag_id INT NOT NULL COMMENT 'tagId,PK2',
	pos INT COMMENT '位置,在APP页面中的位置',
	creation_date TIMESTAMP (3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建时间',
	PRIMARY KEY (platform_id, tag_id)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

--0904
ALTER TABLE mgt_accounts ADD COLUMN  trade_password varchar(200) comment '交易密码';

--0905 创建表mgt_user_lc_devices
CREATE TABLE mgt_user_lc_devices (
  object_id varchar(255) NOT NULL DEFAULT '',
  user_id int(10) NOT NULL,
  device_type varchar(20) NOT NULL,
  installation_id varchar(255) DEFAULT NULL,
  device_token varchar(255) DEFAULT NULL,
  creation_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_date timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  status tinyint(4) DEFAULT '0',
  app_name  varchar(10) DEFAULT NULL,
  PRIMARY KEY (object_id),
  KEY i_user_devices_uid (user_id),
  KEY i_user_devices_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--0906增加列
ALTER TABLE mgt_user_event_logs ADD COLUMN  event_value2 VARCHAR(20) comment '事件值2';

--0907
ALTER TABLE mgt_loan_platforms MODIFY COLUMN approval_time VARCHAR(20) comment '审批时间';

--mgt_xjl_pay_records
CREATE TABLE mgt_xjl_pay_records (
  order_id varchar(50) NOT NULL COMMENT '订单号',
  custom_number varchar(15) NOT NULL COMMENT '商户号',
  amount int(11) NOT NULL COMMENT '金额, 单位分',
  bank_code varchar(10) DEFAULT NULL COMMENT '银行编号。如ICBC',
  bank_account_number varchar(30) NOT NULL COMMENT '银行账号',
  bank_account_name varchar(50) NOT NULL COMMENT '银行账户名',
  biz varchar(10) NOT NULL COMMENT '	业务类型。 B2B对公，B2C 对私。',
  bank_union_code varchar(20) DEFAULT NULL COMMENT '银行联行号',
  fee_type varchar(20) NOT NULL COMMENT '续费收取方式。PAYER:付款方收取手续费 RECEIVER:收款方收取手续费',
  urgency tinyint(4) DEFAULT NULL COMMENT '	是否加急',
  summary varchar(200) DEFAULT NULL COMMENT '打款备注',
  serial_number varchar(50) DEFAULT NULL COMMENT '支付系统返回的唯一流水号',
  order_status varchar(50) DEFAULT NULL COMMENT '打款状态。RECEIVE 已接收 INIT 初始化 DOING处理中 SUCCESS成功 FAIL失败 REFUND退款',
  creation_date timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  update_date timestamp(3) NULL DEFAULT NULL COMMENT '更新时间',
  account_id int(11) DEFAULT NULL COMMENT '执行账号ID',
  applyId int(11) DEFAULT NULL COMMENT '申请借款applyId',
  ret_reason varchar(250) DEFAULT NULL COMMENT '订单失败或成功原因',
  PRIMARY KEY (order_id)
) 

--0910
ALTER TABLE mgt_xjl_agreements ADD COLUMN org_id int comment '组织ID';
ALTER TABLE mgt_xjl_agreements ADD COLUMN agreement_url varchar(200) comment '协议H5地址';

--0911
ALTER TABLE mgt_channels ADD COLUMN msg_times INT DEFAULT 0 comment '提醒次数';

--0912
ALTER TABLE mgt_fin_org_sales_relation ADD COLUMN delegate_price int(11) DEFAULT 0 comment '代理价格';

--0912
ALTER TABLE mgt_org_users ADD COLUMN jbb_flag tinyint default 0 comment '是否代理';

--0914
ALTER TABLE mgt_fin_channel_statistic_daily ADD COLUMN ad_cnt int default 0 comment '广告代理';

--0920
ALTER TABLE mgt_loan_platform_statistic ADD COLUMN click_cnt int default 0 comment '点击数';

--0927
alter table mgt_loan_platform_detail_statistic modify column type varchar(20);

-- 0927 创建表mgt_loan_channel_statistic_daily
CREATE TABLE mgt_loan_channel_statistic_daily (
  statistic_date Date COMMENT '日期',
  channel_code VARCHAR(20) NOT NULL COMMENT '渠道号',
  channel_name varchar(20) COMMENT '渠道名称',
  app_login_num INT NOT NULL COMMENT 'app登录数',
  platform_uv INT NOT NULL COMMENT '贷超平台uv',
  platform_pv INT NOT NULL COMMENT '贷超平台pv',
  PRIMARY KEY (statistic_date,channel_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 0929 修改渠道表
ALTER TABLE mgt_channels ADD COLUMN channel_app_name VARCHAR(20) comment '对外产品名称';
ALTER TABLE mgt_channels ADD COLUMN h5_theme VARCHAR(20) default 'themedefault' comment 'h5主题样式';
ALTER TABLE mgt_channels ADD COLUMN download_app VARCHAR(10) default 'jbb' comment '下载的app';
ALTER TABLE mgt_channels ADD COLUMN test_flag  tinyint default 0 comment '测试标识';
ALTER TABLE mgt_channels ADD COLUMN group_name VARCHAR(10) comment '渠道分组';
ALTER TABLE `mgt_channels` ADD INDEX i_group_name (group_name)

-- 1017 渠道表增加扣量相关字段
ALTER TABLE mgt_channels ADD COLUMN reduce_enable  tinyint(4) default 0 comment '扣量开关,默认关闭';
ALTER TABLE mgt_channels ADD COLUMN reduce_percent  int(11) default 0 comment '扣量百分比,默认0';

-- 1018 修改渠道表中的group_name字段的长度有10变为50
alter table mgt_channels modify column group_name varchar(50);