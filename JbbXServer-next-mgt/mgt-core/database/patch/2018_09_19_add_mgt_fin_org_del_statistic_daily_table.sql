CREATE TABLE mgt_fin_org_del_statistic_daily
(
   statistic_date date comment '统计时间',
   org_id  int comment '组织ID',
   cnt int DEFAULT 0 comment '统计数',
   price int DEFAULT 0 comment '单价， 单位为分',
   expense int DEFAULT 0 comment '消耗额',
   amount int DEFAULT 0 comment '收款额',
   balance int DEFAULT 0 comment '余额',
   status int(4) comment '余额',
   creation_date timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) comment '创建时间',
   update_date timestamp(3) comment '修改时间',
   confirm_date timestamp(3) comment '确认时间',
   confrim_account_id int comment '确认人',
   PRIMARY KEY (statistic_date,org_id)
)
ENGINE InnoDB;