CREATE TABLE mgt_channel_crawler_users
(
   id int not null AUTO_INCREMENT,
   channel_code varchar(20) comment '渠道号',
   merchant_id int comment '商户id',
   user_name varchar(20) comment '姓名',
   phone_number varchar(20) comment '电话',
   u_id varchar(20) comment '标示id',
   register_date timestamp comment '注册时间',
   creation_date  timestamp comment '创建时间',
   PRIMARY KEY (id)
)
ENGINE InnoDB;