CREATE TABLE mgt_fin_org_sales_relation
(
   org_id  int comment '组织id',
   account_id  int comment '市场员账号',
   creator int comment '添加人id',
   creation_date timestamp(3) comment '创建日期',
   is_deleted tinyint comment '是否删除' default 0,
   default_price  int comment '默认价格',
   PRIMARY KEY (org_id)
)
ENGINE InnoDB;