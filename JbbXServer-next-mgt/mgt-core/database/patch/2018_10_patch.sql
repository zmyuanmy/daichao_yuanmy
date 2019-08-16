
-- 你我金融信息回流日志表
CREATE TABLE mgt_xjl_flowback_log
(
   id int not null auto_increment,
   user_id int not null comment '用户id',
   device_id varchar(100) not null comment '设备id',
   status varchar(10) comment '回流状态 0表示回流提交失败，1表示成功，后续有状态码可添加',
   type varchar(20) comment '业务类型 对应你我金融业务码',
   creation_date Timestamp not null default CURRENT_TIMESTAMP() comment '创建时间',
   PRIMARY KEY (id)
)
ENGINE InnoDB;

-- 你我金融反欺诈接口调用流水表
CREATE TABLE mgt_xjl_boss_order
(
   order_id varchar(50),
   user_id int not null comment '用户id',
   apply_id  int not null comment '借款记录id',
   status  varchar(10) comment '调用状态',
   notify_status  varchar(10) comment '回调转态 0 未回调 1 已回调',
   decision varchar(10) comment ' 0:accept 1:refuse 2:review' ,
   result text comment '调用结果 json 串',
   creation_date Timestamp not null default CURRENT_TIMESTAMP() comment '创建时间',
   update_date  Timestamp comment '更新时间',
   PRIMARY KEY (order_id)
)
ENGINE InnoDB;

