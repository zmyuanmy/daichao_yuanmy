--0815

--支付产品表
CREATE TABLE mgt_xjl_pay_products (
  pay_product_id VARCHAR (10) NOT NULL COMMENT '产品编号',
  pay_product_name VARCHAR (20) COMMENT '产品名称',
  creation_date timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  PRIMARY KEY(pay_product_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

--支付所支持银行表
CREATE TABLE mgt_xjl_pay_banks (
  pay_product_id VARCHAR (10) NOT NULL COMMENT '产品编号',
  bank_code VARCHAR (10) NOT NULL COMMENT '银行编码',
  bank_name VARCHAR (20) COMMENT '银行名称',
  card_type INT NOT NULL COMMENT '卡种  1-借记卡，2-贷记卡',
  max_per INT DEFAULT NULL COMMENT '每笔限额 ，单位分',
  max_day INT DEFAULT NULL COMMENT '每天限额 ，单位分',
  max_month INT DEFAULT NULL COMMENT '每月限额 ，单位分',
  creation_date timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  PRIMARY KEY(pay_product_id,bank_code,card_type)
)ENGINE=INNODB DEFAULT CHARSET=utf8;
ALTER TABLE mgt_xjl_pay_banks ADD CONSTRAINT fk_banks_pay_product_id FOREIGN KEY (pay_product_id) REFERENCES mgt_xjl_pay_products(pay_product_id);

--0829
ALTER TABLE mgt_xjl_pay_banks ADD COLUMN bank_color varchar(10) comment '银行背景色';
ALTER TABLE mgt_xjl_pay_banks ADD COLUMN bank_logo varchar(20) comment '银行图标名';

--0906
ALTER TABLE mgt_xjl_pay_banks ADD COLUMN bank_card_number int comment '银行卡序号';

--支付产品
INSERT INTO mgt_xjl_pay_products(pay_product_id,pay_product_name) VALUES('hlb','合利宝');

--支付所支持银行
INSERT INTO mgt_xjl_pay_banks (pay_product_id, bank_code, bank_name, card_type, max_per, max_day, max_month, creation_date, bank_color, bank_logo, bank_card_number) VALUES ('hlb', 'ABC', '农业银行', 1, 500000, 1000000, NULL, '2018-8-16 15:16:36', '#27AF64', 'ABC.png', 3);
INSERT INTO mgt_xjl_pay_banks (pay_product_id, bank_code, bank_name, card_type, max_per, max_day, max_month, creation_date, bank_color, bank_logo, bank_card_number) VALUES ('hlb', 'BCCB', '北京银行', 1, 500000, 500000, NULL, '2018-8-16 15:16:36', '#ED4351', 'BCCB.png', 15);
INSERT INTO mgt_xjl_pay_banks (pay_product_id, bank_code, bank_name, card_type, max_per, max_day, max_month, creation_date, bank_color, bank_logo, bank_card_number) VALUES ('hlb', 'BOC', '中国银行', 1, 2000000, 3000000, NULL, '2018-8-16 15:16:36', '#ED4351', 'BOC.png', 4);
INSERT INTO mgt_xjl_pay_banks (pay_product_id, bank_code, bank_name, card_type, max_per, max_day, max_month, creation_date, bank_color, bank_logo, bank_card_number) VALUES ('hlb', 'BOCO', '交通银行', 1, 5000000, 5000000, NULL, '2018-8-16 15:16:36', '#517FE7', 'BOCO.png', 5);
INSERT INTO mgt_xjl_pay_banks (pay_product_id, bank_code, bank_name, card_type, max_per, max_day, max_month, creation_date, bank_color, bank_logo, bank_card_number) VALUES ('hlb', 'CCB', '建设银行', 1, 5000000, 5000000, NULL, '2018-8-16 15:16:36', '#517FE7', 'CCB.png', 2);
INSERT INTO mgt_xjl_pay_banks (pay_product_id, bank_code, bank_name, card_type, max_per, max_day, max_month, creation_date, bank_color, bank_logo, bank_card_number) VALUES ('hlb', 'CEB', '光大银行', 1, 5000000, 5000000, NULL, '2018-8-16 15:16:36', '#FDD000', 'CEB.png', 11);
INSERT INTO mgt_xjl_pay_banks (pay_product_id, bank_code, bank_name, card_type, max_per, max_day, max_month, creation_date, bank_color, bank_logo, bank_card_number) VALUES ('hlb', 'CGB', '广发银行', 1, 2000000, 2000000, NULL, '2018-8-16 15:16:36', '#ED4351', 'CGB.png', 14);
INSERT INTO mgt_xjl_pay_banks (pay_product_id, bank_code, bank_name, card_type, max_per, max_day, max_month, creation_date, bank_color, bank_logo, bank_card_number) VALUES ('hlb', 'CIB', '兴业银行', 1, 5000000, 5000000, NULL, '2018-8-16 15:16:36', '#517FE7', 'CIB.png', 7);
INSERT INTO mgt_xjl_pay_banks (pay_product_id, bank_code, bank_name, card_type, max_per, max_day, max_month, creation_date, bank_color, bank_logo, bank_card_number) VALUES ('hlb', 'CMBCHINA', '招商银行', 1, 500000, 500000, NULL, '2018-8-16 15:16:36', '#ED4351', 'CMB.png', 6);
INSERT INTO mgt_xjl_pay_banks (pay_product_id, bank_code, bank_name, card_type, max_per, max_day, max_month, creation_date, bank_color, bank_logo, bank_card_number) VALUES ('hlb', 'CMBC', '民生银行', 1, 2000000, 2000000, NULL, '2018-8-16 15:16:36', '#27AF64', 'CMBC.png', 9);
INSERT INTO mgt_xjl_pay_banks (pay_product_id, bank_code, bank_name, card_type, max_per, max_day, max_month, creation_date, bank_color, bank_logo, bank_card_number) VALUES ('hlb', 'ECITIC', '中信银行', 1, 5000000, 5000000, NULL, '2018-8-16 15:16:36', '#ED4351', 'ECITIC.png', 8);
INSERT INTO mgt_xjl_pay_banks (pay_product_id, bank_code, bank_name, card_type, max_per, max_day, max_month, creation_date, bank_color, bank_logo, bank_card_number) VALUES ('hlb', 'HXB', '华夏银行', 1, 200000, 200000, NULL, '2018-8-16 15:16:36', '#ED4351', 'HXB.png', 13);
INSERT INTO mgt_xjl_pay_banks (pay_product_id, bank_code, bank_name, card_type, max_per, max_day, max_month, creation_date, bank_color, bank_logo, bank_card_number) VALUES ('hlb', 'ICBC', '工商银行', 1, 5000000, 5000000, NULL, '2018-8-16 15:16:36', '#ED4351', 'ICBC.png', 1);
INSERT INTO mgt_xjl_pay_banks (pay_product_id, bank_code, bank_name, card_type, max_per, max_day, max_month, creation_date, bank_color, bank_logo, bank_card_number) VALUES ('hlb', 'PINGAN', '平安银行', 1, 2000000, 2000000, NULL, '2018-8-16 15:16:36', '#FDD000', 'PINGAN.png', 12);
INSERT INTO mgt_xjl_pay_banks (pay_product_id, bank_code, bank_name, card_type, max_per, max_day, max_month, creation_date, bank_color, bank_logo, bank_card_number) VALUES ('hlb', 'SHB', '上海银行', 1, 5000000, 5000000, NULL, '2018-8-16 15:16:36', '#FDD000', 'SHB.png', 16);
INSERT INTO mgt_xjl_pay_banks (pay_product_id, bank_code, bank_name, card_type, max_per, max_day, max_month, creation_date, bank_color, bank_logo, bank_card_number) VALUES ('hlb', 'SPDB', '浦发银行', 1, 5000000, 5000000, NULL, '2018-8-16 15:16:36', '#517FE7', 'SPDB.png', 10);

