CREATE TABLE mgt_fin_org_statistic_daily 
(
   statistic_date date comment '统计时间',
   org_id  int comment '组织ID',
   type  int comment '类型 1：进件，2注册',
   cnt int comment '统计数',
   price int comment '单价， 单位为分',
   expense int comment '消耗额',
   amount int comment '收款额',
   balance int comment '余额',
   status int(4) comment '余额',
   creation_date timestamp(3) comment '创建时间',
   update_date timestamp(3) comment '修改时间',
   confirm_date timestamp(3) comment '确认时间',
   confrim_account_id int comment '确认人',
   PRIMARY KEY (statistic_date,org_id,type)
)
ENGINE InnoDB;