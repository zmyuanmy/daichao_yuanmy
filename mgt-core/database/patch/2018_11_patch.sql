-- 1114 挖矿日志表
CREATE TABLE eos_dig_history 
(
  miner varchar(12) NOT NULL COMMENT '矿工',
  tx_no varchar(15) NOT NULL COMMENT '编号',
  token varchar(15) DEFAULT NULL COMMENT 'token',
  quantity decimal(10,4) DEFAULT NULL COMMENT '币数量',
  winner int DEFAULT NULL COMMENT '1 输， 0-平， 1-赢',
  winner_quantity decimal(10,4) DEFAULT NULL COMMENT '赢的币数',
  tx_data varchar(200) DEFAULT NULL COMMENT '日志数据',
  creation_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (miner,tx_no)
) ENGINE=InnoDB;

-- 1114 推荐奖励比例
CREATE TABLE eos_token_ref_reward 
(
  token varchar(15) DEFAULT NULL COMMENT 'token',
  ref_percent decimal(5,3) DEFAULT NULL COMMENT ' 推荐人账号返EOS百分比',
  token_percent decimal(5,3) DEFAULT NULL COMMENT '挖矿账号返币百分比',
  ref_token_percent decimal(5,3) DEFAULT NULL COMMENT '推荐人账号返币百分比'
) ENGINE=InnoDB;

--1114
ALTER TABLE mgt_xjl_users_accounts ADD COLUMN  last_login_data timestamp(3)  NULL  comment '最后一次登陆时间';
ALTER TABLE mgt_xjl_users_accounts ADD COLUMN  last_apply_date timestamp(3)  NULL  comment '最后一次登陆时间';





-- 1130
CREATE TABLE mgt_channel_pv
(
   statistic_date date comment '统计时间',
   channel_code varchar(10) comment '渠道编号 ',
   h5_type  int(4) comment 'h5类型：1 进件， 2 注册',
   is_delegate  tinyint comment '是否代理，0 不是， 1 代理',
   pv  int(11) default 0 comment 'PV 数',
   uv  int(11) default 0 comment 'UV 数',
   PRIMARY KEY (statistic_date, channel_code,h5_type, is_delegate )
)
ENGINE InnoDB;

CREATE TABLE mgt_channel_funnel
(
   statistic_date date comment '统计时间',
   channel_code varchar(10) comment '渠道编号 ',
   u_type  int(4) comment '用户类型：0 新用户， 1老用户',
   h5_type  int(4) comment 'h5类型：1 进件， 2 注册',
   is_delegate  tinyint default 0 comment '是否代理，0 不是， 1 代理',
   uv  int(11) default 0 comment 'UV',
   addmore_init_cnt  int(11) default 0 comment '资料页PV',
   addmore_submit_cnt  int(11) default 0 comment '资料提交数',
   success_init_cnt  int(11) default 0 comment '成功页弹窗数',
   success_download_cnt  int(11) default 0 comment '下载数',
   app_login_cnt  int(11) default 0 comment '登录数',
   p_click_cnt  int(11) default 0 comment '贷超PV',
   p_click_uv  int(11) default 0 comment '贷超UV'
   p_uv  int(11) default 0 comment '产品UV'
   PRIMARY KEY (statistic_date, channel_code,u_type, h5_type, is_delegate )
)
ENGINE InnoDB;
