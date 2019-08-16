-- 1210
ALTER TABLE mgt_channel_funnel ADD COLUMN  channle_settlement_cnt int(11)  default 0  comment '渠道结算数';

-- 1205
ALTER TABLE mgt_channels ADD COLUMN  company_required tinyint not null default 0 comment '公司信息';


-- 用户登录统计临时表

CREATE TABLE mgt_user_login_app_tmp
(
   statistic_date date comment '统计时间',
   channel_code varchar(10) comment '渠道编号 ',
   user_id  int(11) not null comment '用户ID',
   PRIMARY KEY (statistic_date, channel_code, user_id)
)
ENGINE InnoDB;


-- 用户登录和点击UV统计
CREATE TABLE mgt_statistic_user_login_puv
(
   statistic_date date comment '统计时间',
   channel_code varchar(10) comment '渠道编号 ',
   app_login_cnt1  int(11) default 0 comment '当天登录数',
   app_login_cnt7  int(11) default 0 comment '7天登录数',
   app_login_cnt15  int(11) default 0 comment '15天登录数',
   app_login_cnt30  int(11) default 0 comment '30天登录数',
   register_cnt1  int(11) default 0 comment '当天注册数',
   register_cnt7  int(11) default 0 comment '7天注册数',
   register_cnt15  int(11) default 0 comment '15天注册数',
   register_cnt30  int(11) default 0 comment '30天注册数',
   p_uv1  int(11) default 0 comment '当天点击产品UV',
   p_uv7  int(11) default 0 comment '7天点击产品UV',
   p_uv15  int(11) default 0 comment '15天点击产品UV',
   p_uv30  int(11) default 0 comment '30天点击产品UV',
   PRIMARY KEY (statistic_date, channel_code)
)

--1227
CREATE TABLE mgt_credit_card_categories (
	category_id INT auto_increment COMMENT '卡片分类ID',
	`name` VARCHAR (10) COMMENT '类名 ',
	`desc` VARCHAR (20) COMMENT '简要描述',
	detail_message VARCHAR (50) COMMENT '详细描述方案',
	icon_url VARCHAR (200) COMMENT '图标地址',
	desc_color VARCHAR (10) COMMENT '简要描述颜色',
	PRIMARY KEY (category_id)
) ENGINE INNODB;

CREATE TABLE mgt_credit_cards (
	credit_id INT auto_increment COMMENT '信用卡信息ID',
	bank_code VARCHAR (10) COMMENT '银行编码缩写',
	bank_name VARCHAR (20) COMMENT '银行名称',
	bank_url VARCHAR (200) COMMENT '银行信用卡资料填写地址',
	card_img_url VARCHAR (200) COMMENT '标签',
	tag_name VARCHAR (10) COMMENT '简要描述颜色',
	tag_color VARCHAR (10) COMMENT '颜色',
	credit_short_name VARCHAR (10) COMMENT '卡片短名字',
	credit_name VARCHAR (20) COMMENT '卡片完整名字',
	credit_desc VARCHAR (20) COMMENT '简要描述',
	gift_logo VARCHAR (200) COMMENT '礼物logo地址',
	gift_desc VARCHAR (20) COMMENT '礼物说明',
	detailed_desc VARCHAR (300) COMMENT '详细说明',
	weight INT COMMENT '权重   -- 用于卡片筛选时排序，运营子系统可调节',
	is_deleted TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除',
	creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建日期',
	PRIMARY KEY (credit_id)
) ENGINE INNODB;

CREATE TABLE mgt_citys (
	zone_id INT COMMENT '地区ID',
	province_id INT DEFAULT NULL COMMENT '省ID',
	province_name VARCHAR (20) DEFAULT NULL COMMENT '省名',
	city_id INT DEFAULT NULL COMMENT '城市ID ',
	city_name VARCHAR (50) DEFAULT NULL COMMENT '城市名',
	PRIMARY KEY (zone_id)
) ENGINE INNODB;

CREATE TABLE mgt_credit_card_areas (
	credit_id INT DEFAULT NULL COMMENT '信用卡ID',
	zone_id INT DEFAULT NULL COMMENT '地区ID'
) ENGINE INNODB;

CREATE TABLE mgt_credit_categorie_cards (
	category_id INT DEFAULT NULL COMMENT '分类ID',
	credit_id INT DEFAULT NULL COMMENT '信用卡ID',
) ENGINE INNODB;

-- 1229
ALTER TABLE mgt_credit_cards ADD COLUMN  apply_cnt int(11) not null  default 0 comment '申请人数';
ALTER TABLE mgt_loan_platforms ADD COLUMN  apply_cnt int(11) not null  default 0 comment '申请人数';

