CREATE TABLE mgt_xjl_user_cards
(
   user_id int not null comment '用户ID',
   bank_code varchar(10) comment '开户银行',
   pay_product_id varchar(10) comment '支付产品ID',
   card_no varchar(20) comment '卡号',
   phone_number varchar(20) comment '卡号',
   is_deleted  tinyint comment '是否删除 默认为0， 1 删除',
   creation_date timestamp comment '创建时间',
   delete_date  timestamp comment '删除时间',
   is_accept_loan_card tinyint   comment '是否为接收款项卡, 默认为0， 1为用户设置的接收放款的卡',
   PRIMARY KEY (card_no,pay_product_id)
)
ENGINE InnoDB;