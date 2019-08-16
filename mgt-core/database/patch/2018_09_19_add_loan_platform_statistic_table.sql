
CREATE TABLE mgt_loan_platform_statistic (
	statistic_date DATE NOT NULL COMMENT '统计时间',
	platform_id INT NOT NULL COMMENT 'H5商家ID ',
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
	uv_cnt INT NOT NULL DEFAULT 0 COMMENT 'UV数',
	total_register_cnt INT NOT NULL DEFAULT 0 COMMENT '注册总数',
	PRIMARY KEY (statistic_date,platform_id)
) ENGINE INNODB;


CREATE TABLE mgt_loan_platform_detail_statistic
(
   id int not null auto_increment,
   statistic_date Date comment'生成日期',
   platform_id int comment '产品id',
   product_platform int comment '区域id',
   category_id int comment '分类id',
   category_name varchar(20) comment '分类名称',
   category_pos int comment '分类的位置',
   pos int comment '产品的位置',
   type varchar(20) comment '类型 ios/andoird',
   click_cnt int comment '点击数',
   uv_cnt int comment '独立用户数',
   PRIMARY KEY (id)
)
ENGINE InnoDB;