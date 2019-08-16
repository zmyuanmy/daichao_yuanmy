ALTER TABLE users ADD COLUMN  trade_password VARCHAR(250) comment '交易密码';
ALTER TABLE users ADD COLUMN  has_trade_password tinyint DEFAULT 0  comment '是否设置交易密码';