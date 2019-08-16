--0313
ALTER TABLE mgt_loan_platforms ADD COLUMN price_mode INT ( 11 ) DEFAULT 1 COMMENT '1. CPA 2 UV';
ALTER TABLE mgt_loan_platforms ADD COLUMN uv_price INT ( 11 ) DEFAULT 0 COMMENT 'UV价格';
ALTER TABLE mgt_loan_platforms ADD COLUMN estimated_uv_cnt INT ( 11 ) DEFAULT 99999 COMMENT '每天估算UV量';
ALTER TABLE mgt_loan_platforms ADD COLUMN min_balance INT ( 11 ) DEFAULT 0 COMMENT '最低待收余额';
ALTER TABLE mgt_loan_platforms ADD COLUMN frozen TINYINT DEFAULT 0 COMMENT '永久下架 1 表示封存，此后不参与统计， 默认为0';