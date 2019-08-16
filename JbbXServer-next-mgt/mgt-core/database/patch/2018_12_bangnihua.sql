-- 大额贷超资料字段
CREATE TABLE mgt_large_loan_fields (
  	field_id INT NOT NULL auto_increment COMMENT '字段id',
	field_name VARCHAR ( 20 ) NOT NULL COMMENT '字段名称(英文)',
	`desc` VARCHAR ( 20 ) NOT NULL COMMENT '字段描述',
	type int	NOT NULL DEFAULT 1 COMMENT '1为填写，2为选择',
	area INT NOT NULL COMMENT '区域',
	display_index INT NOT NULL COMMENT '顺序',
	required TINYINT NOT NULL DEFAULT 0 COMMENT '是否必须',
	is_hidden TINYINT NOT NULL DEFAULT 0 COMMENT '是否隐藏',
PRIMARY KEY ( field_id ) 
);

-- 大额贷超资料字段值表
CREATE TABLE mgt_large_loan_field_values (
	field_id INT NOT NULL COMMENT '字段id',
	field_value VARCHAR ( 20 ) NOT NULL COMMENT '值',
	`desc` VARCHAR ( 20 ) NOT NULL COMMENT '值描述',
	display_index INT NOT NULL COMMENT '显示顺序',
PRIMARY KEY ( field_id, field_value ) 
);

-- 大额贷款机构表
CREATE TABLE mgt_large_loan_orgs  (
  org_id int(11) NOT NULL AUTO_INCREMENT COMMENT '机构id',
  org_name varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '机构名称',
  `desc` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '机构宣传语',
  logo varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'logo',
  price int(11) NOT NULL DEFAULT 0 COMMENT '机构价格',
  is_delete tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
	filter_script VARCHAR(2000) DEFAULT NULL COMMENT '过滤条件',
  creation_date timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (org_id) USING BTREE
);
