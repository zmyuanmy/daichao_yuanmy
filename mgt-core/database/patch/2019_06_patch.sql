ALTER TABLE mgt_loan_area_tags ADD COLUMN freeze TINYINT DEFAULT 0 COMMENT '上下架，0为上架，1为下架';
update mgt_info set db_version = 10;

--0606
--短信日志记录表
CREATE TABLE mgt_message_logs (
	msg_id VARCHAR ( 50 ) NOT NULL COMMENT '消息ID',
	phone_number VARCHAR ( 20 ) NOT NULL  COMMENT '手机号码',
	channel_code VARCHAR ( 20 ) NOT NULL COMMENT '渠道号',
	remote_address VARCHAR ( 50 ) NULL DEFAULT NULL COMMENT '四位ip地址',
	creation_date TIMESTAMP ( 3 ) NOT NULL DEFAULT CURRENT_TIMESTAMP ( 3 ) COMMENT '创建时间',
	PRIMARY KEY ( msg_id )
);

--短信回调报告表
CREATE TABLE mgt_message_report (
	msg_id VARCHAR ( 50 ) NOT NULL COMMENT '消息ID',
	report_time VARCHAR ( 50 ) NULL DEFAULT NULL COMMENT '运营商返回的状态更新时间',
	mobile VARCHAR ( 20 ) NOT NULL COMMENT '手机号',
	`status` VARCHAR ( 20 ) NOT NULL COMMENT '状态',
	notify_time VARCHAR ( 50 ) NULL DEFAULT NULL COMMENT '回调时间',
	status_desc VARCHAR ( 256 ) NULL DEFAULT NULL COMMENT '状态说明',
	uid VARCHAR ( 50 ) NULL DEFAULT NULL COMMENT '发送记录流水号',
	length INT ( 11 ) NULL DEFAULT NULL COMMENT '下发短信计费条数',
PRIMARY KEY ( msg_id )
);
update mgt_info set db_version = 11;
