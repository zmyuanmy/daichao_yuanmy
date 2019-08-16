-- 0103
ALTER TABLE mgt_loan_platforms ADD COLUMN sales_id int(11)  default null  comment '销售人员的accountId';

-- 0104 为销售商务加上 贷超冲销权限
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES ('103', '119');

ALTER TABLE mgt_channels ADD COLUMN uv_price int(11)  default 0  comment 'uv价格';

ALTER TABLE mgt_fin_channel_statistic_daily ADD COLUMN uv_cnt int(11)  default 0  comment 'uv数量';

ALTER TABLE mgt_fin_channel_statistic_daily ADD COLUMN mode int(11)  default NULL  comment '结算模式';

ALTER TABLE mgt_loan_platform_statistic ADD COLUMN puv_cnt int(11)  default 0  comment '产品uv';

-- 0102
ALTER TABLE mgt_users ADD COLUMN  image VARCHAR(200)  default null comment '头像';
ALTER TABLE mgt_users ADD COLUMN  profession VARCHAR(50)  default null comment '职业';

-- 0107 新增版本信息表
CREATE TABLE mgt_app_info (
  app_name varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'app名称',
  app_desc varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'app描述',
  wechat varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '联系微信',
  phone_number varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人电话',
  contact_name varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人姓名',
  company_name varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '公司名称',
  company_desc varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '公司简介',
  PRIMARY KEY (app_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--0124
ALTER TABLE mgt_app_info ADD COLUMN qq VARCHAR(20)  default NULL  comment '客服qq';

--0123 贷超支持配置ios或者android
ALTER TABLE mgt_loan_platforms ADD COLUMN  is_support_ios tinyint not null default 1 comment '是否支持ios';
ALTER TABLE mgt_loan_platforms ADD COLUMN  is_support_android tinyint not null default 1 comment '是否支持android';

-- ----------------------------
-- 新增续期日志表 mgt_xjl_renewal_log 1000539
-- ----------------------------
CREATE TABLE mgt_xjl_renewal_log (
  log_id int(11) NOT NULL AUTO_INCREMENT,
  apply_id varchar(100) COLLATE utf8_bin NOT NULL,
  user_id int(11) DEFAULT NULL COMMENT '用户id',
  account_id int(11) DEFAULT NULL COMMENT '管理后台用户id',
  op_type int(11) DEFAULT NULL COMMENT '操作类型 1 为 管理后台用户操作 2 为用户操作',
  creation_date timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (log_id)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
SET FOREIGN_KEY_CHECKS=1;


ALTER TABLE mgt_xjl_apply_records ADD COLUMN renewal_cnt INT(11)  DEFAULT '1' COMMENT '续期次数';
ALTER TABLE mgt_xjl_apply_records ADD COLUMN interest INT(11)  COMMENT '利息';
ALTER TABLE mgt_xjl_apply_records ADD COLUMN loan_again TINYINT(4)  DEFAULT '0' COMMENT '是否复借:0首借,1复借';
ALTER TABLE mgt_xjl_apply_records ADD COLUMN late_fee INT(11)  DEFAULT '0' COMMENT '滞纳金 默认为0';

 ALTER TABLE mgt_xjl_apply_records MODIFY COLUMN apply_id VARCHAR(100);
 ALTER TABLE mgt_xjl_apply_record_op_logs MODIFY COLUMN apply_id VARCHAR(100);
 ALTER TABLE mgt_xjl_boss_order MODIFY COLUMN apply_id VARCHAR(100);
 ALTER TABLE mgt_xjl_message_detail MODIFY COLUMN apply_id VARCHAR(100);

 ALTER TABLE mgt_xjl_pay_records MODIFY COLUMN applyId VARCHAR(100);

 ALTER TABLE mgt_xjl_user_orders MODIFY COLUMN apply_id VARCHAR(100);
 ALTER TABLE mgt_xjl_pay_records MODIFY COLUMN apply_id VARCHAR(100);


 -- ----------------------------
-- 新增用户投诉表 for mgt_user_complaint 2019-01-24 1000580
-- ----------------------------
CREATE TABLE mgt_user_complaint (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  user_id int(11) DEFAULT NULL COMMENT '用户ID',
  content varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '投诉内容',
  app_name varchar(20) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
SET FOREIGN_KEY_CHECKS=1;

-- 0125 帮你花'关于我们' 增加客服qq
INSERT INTO `mgt_app_info` VALUES ('bnh', '帮你花是一个服务于个人用户和金融机构的智慧金融平台，为金融机构和个人搭建起智慧金融互联桥梁；纯手机线上信用借款，轻松解决缺钱难题，为您提供专业全程线上借贷服务！', '13691269303', 'bnh的电话', 'bnh', 'bnh', '帮你花是一个服务于个人用户和金融机构的智慧金融平台，为金融机构和个人搭建起智慧金融互联桥梁；纯手机线上信用借款，轻松解决缺钱难题，为您提供专业全程线上借贷服务！', '669342115');
