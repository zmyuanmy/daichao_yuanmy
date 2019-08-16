CREATE TABLE mgt_fin_files
(
   file_id int not null auto_increment,
   org_id int comment '组织id',
   merchant_id int comment '商户id',
   file_name varchar(100) comment '用户ID',
   file_type int comment '组织区分，1为进件组织文件，2为注册组织文件',
   creator int comment '添加人id',
   creation_date timestamp(3) comment '创建日期',
   file_date timestamp(3) comment '绑定日期，默认为添加时间',
   is_deleted tinyint comment '是否删除' default 0,
   PRIMARY KEY (file_id)
)
ENGINE InnoDB;