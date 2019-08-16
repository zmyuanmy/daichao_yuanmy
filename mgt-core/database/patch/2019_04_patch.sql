
--0411
ALTER TABLE mgt_loan_platforms ADD unique(short_name);

-- 0411 新建手机号黑名单表
CREATE TABLE mgt_phone_blacklist (
  blacklist_id int(11) NOT NULL AUTO_INCREMENT,
  phone_number varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP() COMMENT '创建日期',
  record_cnt int(11) NOT NULL COMMENT '入库次数',
  recent_record_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP() comment '最近更新入库时间',
  record_reason varchar(255) DEFAULT NULL COMMENT '入库原因',
  PRIMARY KEY (blacklist_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--0423
ALTER TABLE mgt_loan_platforms ADD COLUMN  is_home_page_push TINYINT DEFAULT 0 COMMENT '是否首页强推';

--0429
ALTER TABLE mgt_users ADD COLUMN phone_number_md5 VARCHAR(50) DEFAULT NULL COMMENT '手机号MD5';
alter table mgt_users add index i_mgt_user_pmd5 (phone_number_md5);