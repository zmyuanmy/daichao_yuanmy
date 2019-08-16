CREATE TABLE mgt_xjl_user_orders
(
   order_id varchar(50) not null comment '订单流水号',
   user_id int comment '用户id',
   apply_id int comment '申请单id',
   type varchar(50) comment '交易类型',
   status varchar(20) comment '交易状态 默认为COMMIT ,SUCCESS,FAILED,CANCELLED,REFUNDED,DOING',
   creation_date timestamp comment '订单创建日期',
   update_date timestamp comment '订单更新日期',
   expiry_date timestamp comment '订单失效时间',
   ip_address varchar(20) comment 'ip地址',
   terminal_type varchar(20) comment '设备终端类型',
   terminal_id varchar(50) comment '设备终端id',
   card_no varchar(20) comment '银行卡号',
   pay_product_id varchar(10) comment '支付产品id，默认为合利宝',
   PRIMARY KEY (order_id)
)
ENGINE InnoDB;