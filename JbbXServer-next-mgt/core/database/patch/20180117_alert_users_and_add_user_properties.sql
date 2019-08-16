ALTER TABLE `users` ADD sex VARCHAR(2) COMMENT '性别',
ADD age TINYINT(4) COMMENT '年龄',
ADD nation VARCHAR(20) COMMENT '名族',
ADD idcard_address VARCHAR(50) COMMENT '身份证所在地',
ADD wechat VARCHAR(50) COMMENT '微信',
ADD qq_number VARCHAR(20) COMMENT 'QQ',
ADD address_book_number INT(11) COMMENT '通讯录人数',
ADD phone_authentication_time VARCHAR(20) COMMENT '手机实名多久',
ADD is_married TINYINT(2) COMMENT '0：已婚/1：未婚', 
ADD live_address VARCHAR(50) COMMENT '现居住地',
ADD parent_address VARCHAR(50) COMMENT '父母住地址',
ADD education VARCHAR(20) COMMENT '学历',
ADD occupation VARCHAR(20) COMMENT '职业';

DROP TABLE IF EXISTS user_properties;

CREATE TABLE `user_properties` (
  `user_id` int(11) NOT NULL COMMENT '外键指向users的user_id',
  `name` varchar(50) NOT NULL COMMENT '属性名',
  `value` varchar(500) DEFAULT NULL COMMENT '属性值',
  PRIMARY KEY (`user_id`,`name`),
  CONSTRAINT `fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

