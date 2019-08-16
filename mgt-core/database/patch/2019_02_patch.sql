-- 优化表查询 
ALTER TABLE mgt_fin_files ADD INDEX  i_fin_files_pid (platform_id);
ALTER TABLE mgt_fin_files ADD INDEX  i_fin_files_is_deleted (is_deleted);
ALTER TABLE mgt_fin_files ADD INDEX  i_fin_files_file_date (file_date);


-- jbbserver
ALTER TABLE users ADD INDEX  i_users_role_id (role_id);


-- 百融黑名单检测表

CREATE TABLE mgt_mobile_blacklist_check
(
   batch_id int not null comment '批次ID',
   phone_number varchar(20) not null comment '手机号',
   channel_code varchar(20) not null comment '渠道号',
   creation_date timestamp(3) comment '创建日期',
   blacklist_num int default 0 comment '黑名单数',
   status tinyint comment '状态，1 抽样 2 检测完成',
   check_date timestamp(3) comment '检测时间',
  	flag_specialList_c varchar(10) comment '产品输出标识',
	sl_id_court_bad varchar(10) comment '通过身份证号查询法院失信人',
	sl_id_court_executed varchar(10) comment '通过身份证号查询法院被执行人',
	sl_id_bad_info varchar(10) comment '通过身份证查询公安信息异常',
	sl_id_bank_bad varchar(10) comment '通过身份证号查询银行(含信用卡)中风险',
	sl_id_bank_overdue varchar(10) comment '通过身份证号查询银行(含信用卡)一般风险',
	sl_id_bank_fraud varchar(10) comment '通过身份证号查询银行(含信用卡)资信不佳',
	sl_id_bank_lost varchar(10) comment '通过身份证号查询银行(含信用卡)高风险',
	sl_id_bank_refuse varchar(10) comment '通过身份证号查询银行(含信用卡)拒绝',
	sl_id_nbank_bad varchar(10) comment '通过身份证号查询非银(含全部非银类型)中风险',
	sl_id_nbank_overdue varchar(10) comment '通过身份证号查询非银(含全部非银类型)一般风险',
	sl_id_nbank_fraud varchar(10) comment '通过身份证号查询非银(含全部非银类型)资信不佳',
	sl_id_nbank_lost varchar(10) comment '通过身份证号查询非银(含全部非银类型)高风险',
	sl_id_nbank_refuse varchar(10) comment '通过身份证号查询非银(含全部非银类型)拒绝',
	sl_id_nbank_nsloan_bad varchar(10) comment '通过身份证号查询非银-持牌网络小贷中风险',
	sl_id_nbank_nsloan_overdue varchar(10) comment '通过身份证号查询非银-持牌网络小贷一般风险',
	sl_id_nbank_nsloan_fraud varchar(10) comment '通过身份证号查询非银-持牌网络小贷资信不佳',
	sl_id_nbank_nsloan_lost varchar(10) comment '通过身份证号查询非银-持牌网络小贷高风险',
	sl_id_nbank_nsloan_refuse varchar(10) comment '通过身份证号查询非银-持牌网络小贷拒绝',
	sl_id_nbank_sloan_bad varchar(10) comment '通过身份证号查询非银-持牌小贷中风险',
	sl_id_nbank_sloan_overdue varchar(10) comment '通过身份证号查询非银-持牌小贷一般风险',
	sl_id_nbank_sloan_fraud varchar(10) comment '通过身份证号查询非银-持牌小贷资信不佳',
	sl_id_nbank_sloan_lost varchar(10) comment '通过身份证号查询非银-持牌小贷高风险',
	sl_id_nbank_sloan_refuse varchar(10) comment '通过身份证号查询非银-持牌小贷拒绝',
	sl_id_nbank_cons_bad varchar(10) comment '通过身份证号查询非银-持牌消费金融中风险',
	sl_id_nbank_cons_overdue varchar(10) comment '通过身份证号查询非银-持牌消费金融一般风险',
	sl_id_nbank_cons_fraud varchar(10) comment '通过身份证号查询非银-持牌消费金融资信不佳',
	sl_id_nbank_cons_lost varchar(10) comment '通过身份证号查询非银-持牌消费金融高风险',
	sl_id_nbank_cons_refuse varchar(10) comment '通过身份证号查询非银-持牌消费金融拒绝',
	sl_id_nbank_finlea_bad varchar(10) comment '通过身份证号查询非银-持牌融资租赁中风险',
	sl_id_nbank_finlea_overdue varchar(10) comment '通过身份证号查询非银-持牌融资租赁一般风险',
	sl_id_nbank_finlea_fraud varchar(10) comment '通过身份证号查询非银-持牌融资租赁资信不佳',
	sl_id_nbank_finlea_lost varchar(10) comment '通过身份证号查询非银-持牌融资租赁高风险',
	sl_id_nbank_finlea_refuse varchar(10) comment '通过身份证号查询非银-持牌融资租赁拒绝',
	sl_id_nbank_autofin_bad varchar(10) comment '通过身份证号查询非银-持牌汽车金融中风险',
	sl_id_nbank_autofin_overdue varchar(10) comment '通过身份证号查询非银-持牌汽车金融一般风险',
	sl_id_nbank_autofin_fraud varchar(10) comment '通过身份证号查询非银-持牌汽车金融资信不佳',
	sl_id_nbank_autofin_lost varchar(10) comment '通过身份证号查询非银-持牌汽车金融高风险',
	sl_id_nbank_autofin_refuse varchar(10) comment '通过身份证号查询非银-持牌汽车金融拒绝',
	sl_id_nbank_other_bad varchar(10) comment '通过身份证号查询非银-其他中风险',
	sl_id_nbank_other_overdue varchar(10) comment '通过身份证号查询非银-其他一般风险',
	sl_id_nbank_other_fraud varchar(10) comment '通过身份证号查询非银-其他资信不佳',
	sl_id_nbank_other_lost varchar(10) comment '通过身份证号查询非银-其他高风险',
	sl_id_nbank_other_refuse varchar(10) comment '通过身份证号查询非银-其他拒绝',
	sl_cell_bank_bad varchar(10) comment '通过手机号查询银行(含信用卡)中风险',
	sl_cell_bank_overdue varchar(10) comment '通过手机号查询银行(含信用卡)一般风险',
	sl_cell_bank_fraud varchar(10) comment '通过手机号查询银行(含信用卡)资信不佳',
	sl_cell_bank_lost varchar(10) comment '通过手机号查询银行(含信用卡)高风险',
	sl_cell_bank_refuse varchar(10) comment '通过手机号查询银行(含信用卡)拒绝',
	sl_cell_nbank_bad varchar(10) comment '通过手机号查询非银(含全部非银类型)中风险',
	sl_cell_nbank_overdue varchar(10) comment '通过手机号查询非银(含全部非银类型)一般风险',
	sl_cell_nbank_fraud varchar(10) comment '通过手机号查询非银(含全部非银类型)资信不佳',
	sl_cell_nbank_lost varchar(10) comment '通过手机号查询非银(含全部非银类型)高风险',
	sl_cell_nbank_refuse varchar(10) comment '通过手机号查询非银(含全部非银类型)拒绝',
	sl_cell_nbank_nsloan_bad varchar(10) comment '通过手机号查询非银-持牌网络小贷中风险',
	sl_cell_nbank_nsloan_overdue varchar(10) comment '通过手机号查询非银-持牌网络小贷一般风险',
	sl_cell_nbank_nsloan_fraud varchar(10) comment '通过手机号查询非银-持牌网络小贷资信不佳',
	sl_cell_nbank_nsloan_lost varchar(10) comment '通过手机号查询非银-持牌网络小贷高风险',
	sl_cell_nbank_nsloan_refuse varchar(10) comment '通过手机号查询非银-持牌网络小贷拒绝',
	sl_cell_nbank_sloan_bad varchar(10) comment '通过手机号查询非银-持牌小贷中风险',
	sl_cell_nbank_sloan_overdue varchar(10) comment '通过手机号查询非银-持牌小贷一般风险',
	sl_cell_nbank_sloan_fraud varchar(10) comment '通过手机号查询非银-持牌小贷资信不佳',
	sl_cell_nbank_sloan_lost varchar(10) comment '通过手机号查询非银-持牌小贷高风险',
	sl_cell_nbank_sloan_refuse varchar(10) comment '通过手机号查询非银-持牌小贷拒绝',
	sl_cell_nbank_cons_bad varchar(10) comment '通过手机号查询非银-持牌消费金融中风险',
	sl_cell_nbank_cons_overdue varchar(10) comment '通过手机号查询非银-持牌消费金融一般风险',
	sl_cell_nbank_cons_fraud varchar(10) comment '通过手机号查询非银-持牌消费金融资信不佳',
	sl_cell_nbank_cons_lost varchar(10) comment '通过手机号查询非银-持牌消费金融高风险',
	sl_cell_nbank_cons_refuse varchar(10) comment '通过手机号查询非银-持牌消费金融拒绝',
	sl_cell_nbank_finlea_bad varchar(10) comment '通过手机号查询非银-持牌融资租赁中风险',
	sl_cell_nbank_finlea_overdue varchar(10) comment '通过手机号查询非银-持牌融资租赁一般风险',
	sl_cell_nbank_finlea_fraud varchar(10) comment '通过手机号查询非银-持牌融资租赁资信不佳',
	sl_cell_nbank_finlea_lost varchar(10) comment '通过手机号查询非银-持牌融资租赁高风险',
	sl_cell_nbank_finlea_refuse varchar(10) comment '通过手机号查询非银-持牌融资租赁拒绝',
	sl_cell_nbank_autofin_bad varchar(10) comment '通过手机号查询非银-持牌汽车金融中风险',
	sl_cell_nbank_autofin_overdue varchar(10) comment '通过手机号查询非银-持牌汽车金融一般风险',
	sl_cell_nbank_autofin_fraud varchar(10) comment '通过手机号查询非银-持牌汽车金融资信不佳',
	sl_cell_nbank_autofin_lost varchar(10) comment '通过手机号查询非银-持牌汽车金融高风险',
	sl_cell_nbank_autofin_refuse varchar(10) comment '通过手机号查询非银-持牌汽车金融拒绝',
	sl_cell_nbank_other_bad varchar(10) comment '通过手机号查询非银-其他中风险',
	sl_cell_nbank_other_overdue varchar(10) comment '通过手机号查询非银-其他一般风险',
	sl_cell_nbank_other_fraud varchar(10) comment '通过手机号查询非银-其他资信不佳',
	sl_cell_nbank_other_lost varchar(10) comment '通过手机号查询非银-其他高风险',
	sl_cell_nbank_other_refuse varchar(10) comment '通过手机号查询非银-其他拒绝',
	sl_lm_cell_bank_bad varchar(10) comment '通过联系人手机查询银行(含信用卡)中风险',
	sl_lm_cell_bank_overdue varchar(10) comment '通过联系人手机查询银行(含信用卡)一般风险',
	sl_lm_cell_bank_fraud varchar(10) comment '通过联系人手机查询银行(含信用卡)资信不佳',
	sl_lm_cell_bank_lost varchar(10) comment '通过联系人手机查询银行(含信用卡)高风险',
	sl_lm_cell_bank_refuse varchar(10) comment '通过联系人手机查询银行(含信用卡)拒绝',
	sl_lm_cell_nbank_bad varchar(10) comment '通过联系人手机号查询非银(含全部非银类型)中风险',
	sl_lm_cell_nbank_overdue varchar(10) comment '通过联系人手机号查询非银(含全部非银类型)一般风险',
	sl_lm_cell_nbank_fraud varchar(10) comment '通过联系人手机号查询非银(含全部非银类型)资信不佳',
	sl_lm_cell_nbank_lost varchar(10) comment '通过联系人手机号查询非银(含全部非银类型)高风险',
	sl_lm_cell_nbank_refuse varchar(10) comment '通过联系人手机号查询非银(含全部非银类型)拒绝',
	sl_lm_cell_nbank_nsloan_bad varchar(10) comment '通过联系人手机号查询非银-持牌网络小贷中风险',
	sl_lm_cell_nbank_nsloan_overdue varchar(10) comment '通过联系人手机号查询非银-持牌网络小贷一般风险',
	sl_lm_cell_nbank_nsloan_fraud varchar(10) comment '通过联系人手机号查询非银-持牌网络小贷资信不佳',
	sl_lm_cell_nbank_nsloan_lost varchar(10) comment '通过联系人手机号查询非银-持牌网络小贷高风险',
	sl_lm_cell_nbank_nsloan_refuse varchar(10) comment '通过联系人手机号查询非银-持牌网络小贷拒绝',
	sl_lm_cell_nbank_sloan_bad varchar(10) comment '通过联系人手机号查询非银-持牌小贷中风险',
	sl_lm_cell_nbank_sloan_overdue varchar(10) comment '通过联系人手机号查询非银-持牌小贷一般风险',
	sl_lm_cell_nbank_sloan_fraud varchar(10) comment '通过联系人手机号查询非银-持牌小贷资信不佳',
	sl_lm_cell_nbank_sloan_lost varchar(10) comment '通过联系人手机号查询非银-持牌小贷高风险',
	sl_lm_cell_nbank_sloan_refuse varchar(10) comment '通过联系人手机号查询非银-持牌小贷拒绝',
	sl_lm_cell_nbank_cons_bad varchar(10) comment '通过联系人手机号查询非银-持牌消费金融中风险',
	sl_lm_cell_nbank_cons_overdue varchar(10) comment '通过联系人手机号查询非银-持牌消费金融一般风险',
	sl_lm_cell_nbank_cons_fraud varchar(10) comment '通过联系人手机号查询非银-持牌消费金融资信不佳',
	sl_lm_cell_nbank_cons_lost varchar(10) comment '通过联系人手机号查询非银-持牌消费金融高风险',
	sl_lm_cell_nbank_cons_refuse varchar(10) comment '通过联系人手机号查询非银-持牌消费金融拒绝',
	sl_lm_cell_nbank_finlea_bad varchar(10) comment '通过联系人手机号查询非银-持牌融资租赁中风险',
	sl_lm_cell_nbank_finlea_overdue varchar(10) comment '通过联系人手机号查询非银-持牌融资租赁一般风险',
	sl_lm_cell_nbank_finlea_fraud varchar(10) comment '通过联系人手机号查询非银-持牌融资租赁资信不佳',
	sl_lm_cell_nbank_finlea_lost varchar(10) comment '通过联系人手机号查询非银-持牌融资租赁高风险',
	sl_lm_cell_nbank_finlea_refuse varchar(10) comment '通过联系人手机号查询非银-持牌融资租赁拒绝',
	sl_lm_cell_nbank_autofin_bad varchar(10) comment '通过联系人手机号查询非银-持牌汽车金融中风险',
	sl_lm_cell_nbank_autofin_overdue varchar(10) comment '通过联系人手机号查询非银-持牌汽车金融一般风险',
	sl_lm_cell_nbank_autofin_fraud varchar(10) comment '通过联系人手机号查询非银-持牌汽车金融资信不佳',
	sl_lm_cell_nbank_autofin_lost varchar(10) comment '通过联系人手机号查询非银-持牌汽车金融高风险',
	sl_lm_cell_nbank_autofin_refuse varchar(10) comment '通过联系人手机号查询非银-持牌汽车金融拒绝',
	sl_lm_cell_nbank_other_bad varchar(10) comment '通过联系人手机查询非银-其他中风险',
	sl_lm_cell_nbank_other_overdue varchar(10) comment '通过联系人手机查询非银-其他一般风险',
	sl_lm_cell_nbank_other_fraud varchar(10) comment '通过联系人手机查询非银-其他资信不佳',
	sl_lm_cell_nbank_other_lost varchar(10) comment '通过联系人手机查询非银-其他高风险',
	sl_lm_cell_nbank_other_refuse varchar(10) comment '通过联系人手机查询非银-其他拒绝',
	sl_id_court_bad_allnum varchar(10) comment '通过身份证号查询法院失信人命中次数',
	sl_id_court_executed_allnum varchar(10) comment '通过身份证号查询法院被执行人命中次数',
	sl_id_bad_info_allnum varchar(10) comment '通过身份证查询公安信息异常命中次数',
	sl_id_bank_bad_allnum varchar(10) comment '通过身份证号查询银行(含信用卡)中风险命中次数',
	sl_id_bank_overdue_allnum varchar(10) comment '通过身份证号查询银行(含信用卡)一般风险命中次数',
	sl_id_bank_fraud_allnum varchar(10) comment '通过身份证号查询银行(含信用卡)资信不佳命中次数',
	sl_id_bank_lost_allnum varchar(10) comment '通过身份证号查询银行(含信用卡)高风险命中次数',
	sl_id_bank_refuse_allnum varchar(10) comment '通过身份证号查询银行(含信用卡)拒绝命中次数',
	sl_id_nbank_bad_allnum varchar(10) comment '通过身份证号查询非银(含全部非银类型)中风险命中次数',
	sl_id_nbank_overdue_allnum varchar(10) comment '通过身份证号查询非银(含全部非银类型)一般风险命中次数',
	sl_id_nbank_fraud_allnum varchar(10) comment '通过身份证号查询非银(含全部非银类型)资信不佳命中次数',
	sl_id_nbank_lost_allnum varchar(10) comment '通过身份证号查询非银(含全部非银类型)高风险命中次数',
	sl_id_nbank_refuse_allnum varchar(10) comment '通过身份证号查询非银(含全部非银类型)拒绝命中次数',
	sl_id_nbank_nsloan_bad_allnum varchar(10) comment '通过身份证号查询非银-持牌网络小贷中风险命中次数',
	sl_id_nbank_nsloan_overdue_allnum varchar(10) comment '通过身份证号查询非银-持牌网络小贷一般风险命中次数',
	sl_id_nbank_nsloan_fraud_allnum varchar(10) comment '通过身份证号查询非银-持牌网络小贷资信不佳命中次数',
	sl_id_nbank_nsloan_lost_allnum varchar(10) comment '通过身份证号查询非银-持牌网络小贷高风险命中次数',
	sl_id_nbank_nsloan_refuse_allnum varchar(10) comment '通过身份证号查询非银-持牌网络小贷拒绝命中次数',
	sl_id_nbank_sloan_bad_allnum varchar(10) comment '通过身份证号查询非银-持牌小贷中风险命中次数',
	sl_id_nbank_sloan_overdue_allnum varchar(10) comment '通过身份证号查询非银-持牌小贷一般风险命中次数',
	sl_id_nbank_sloan_fraud_allnum varchar(10) comment '通过身份证号查询非银-持牌小贷资信不佳命中次数',
	sl_id_nbank_sloan_lost_allnum varchar(10) comment '通过身份证号查询非银-持牌小贷高风险命中次数',
	sl_id_nbank_sloan_refuse_allnum varchar(10) comment '通过身份证号查询非银-持牌小贷拒绝命中次数',
	sl_id_nbank_cons_bad_allnum varchar(10) comment '通过身份证号查询非银-持牌消费金融中风险命中次数',
	sl_id_nbank_cons_overdue_allnum varchar(10) comment '通过身份证号查询非银-持牌消费金融一般风险命中次数',
	sl_id_nbank_cons_fraud_allnum varchar(10) comment '通过身份证号查询非银-持牌消费金融资信不佳命中次数',
	sl_id_nbank_cons_lost_allnum varchar(10) comment '通过身份证号查询非银-持牌消费金融高风险命中次数',
	sl_id_nbank_cons_refuse_allnum varchar(10) comment '通过身份证号查询非银-持牌消费金融拒绝命中次数',
	sl_id_nbank_finlea_bad_allnum varchar(10) comment '通过身份证号查询非银-持牌融资租赁中风险命中次数',
	sl_id_nbank_finlea_overdue_allnum varchar(10) comment '通过身份证号查询非银-持牌融资租赁一般风险命中次数',
	sl_id_nbank_finlea_fraud_allnum varchar(10) comment '通过身份证号查询非银-持牌融资租赁资信不佳命中次数',
	sl_id_nbank_finlea_lost_allnum varchar(10) comment '通过身份证号查询非银-持牌融资租赁高风险命中次数',
	sl_id_nbank_finlea_refuse_allnum varchar(10) comment '通过身份证号查询非银-持牌融资租赁拒绝命中次数',
	sl_id_nbank_autofin_bad_allnum varchar(10) comment '通过身份证号查询非银-持牌汽车金融中风险命中次数',
	sl_id_nbank_autofin_overdue_allnum varchar(10) comment '通过身份证号查询非银-持牌汽车金融一般风险命中次数',
	sl_id_nbank_autofin_fraud_allnum varchar(10) comment '通过身份证号查询非银-持牌汽车金融资信不佳命中次数',
	sl_id_nbank_autofin_lost_allnum varchar(10) comment '通过身份证号查询非银-持牌汽车金融高风险命中次数',
	sl_id_nbank_autofin_refuse_allnum varchar(10) comment '通过身份证号查询非银-持牌汽车金融拒绝命中次数',
	sl_id_nbank_other_bad_allnum varchar(10) comment '通过身份证号查询非银-其他中风险命中次数',
	sl_id_nbank_other_overdue_allnum varchar(10) comment '通过身份证号查询非银-其他一般风险命中次数',
	sl_id_nbank_other_fraud_allnum varchar(10) comment '通过身份证号查询非银-其他资信不佳命中次数',
	sl_id_nbank_other_lost_allnum varchar(10) comment '通过身份证号查询非银-其他高风险命中次数',
	sl_id_nbank_other_refuse_allnum varchar(10) comment '通过身份证号查询非银-其他拒绝命中次数',
	sl_cell_bank_bad_allnum varchar(10) comment '通过手机号查询银行(含信用卡)中风险命中次数',
	sl_cell_bank_overdue_allnum varchar(10) comment '通过手机号查询银行(含信用卡)一般风险命中次数',
	sl_cell_bank_fraud_allnum varchar(10) comment '通过手机号查询银行(含信用卡)资信不佳命中次数',
	sl_cell_bank_lost_allnum varchar(10) comment '通过手机号查询银行(含信用卡)高风险命中次数',
	sl_cell_bank_refuse_allnum varchar(10) comment '通过手机号查询银行(含信用卡)拒绝命中次数',
	sl_cell_nbank_bad_allnum varchar(10) comment '通过手机号查询非银(含全部非银类型)中风险命中次数',
	sl_cell_nbank_overdue_allnum varchar(10) comment '通过手机号查询非银(含全部非银类型)一般风险命中次数',
	sl_cell_nbank_fraud_allnum varchar(10) comment '通过手机号查询非银(含全部非银类型)资信不佳命中次数',
	sl_cell_nbank_lost_allnum varchar(10) comment '通过手机号查询非银(含全部非银类型)高风险命中次数',
	sl_cell_nbank_refuse_allnum varchar(10) comment '通过手机号查询非银(含全部非银类型)拒绝命中次数',
	sl_cell_nbank_nsloan_bad_allnum varchar(10) comment '通过手机号查询非银-持牌网络小贷中风险命中次数',
	sl_cell_nbank_nsloan_overdue_allnum varchar(10) comment '通过手机号查询非银-持牌网络小贷一般风险命中次数',
	sl_cell_nbank_nsloan_fraud_allnum varchar(10) comment '通过手机号查询非银-持牌网络小贷资信不佳命中次数',
	sl_cell_nbank_nsloan_lost_allnum varchar(10) comment '通过手机号查询非银-持牌网络小贷高风险命中次数',
	sl_cell_nbank_nsloan_refuse_allnum varchar(10) comment '通过手机号查询非银-持牌网络小贷拒绝命中次数',
	sl_cell_nbank_sloan_bad_allnum varchar(10) comment '通过手机号查询非银-持牌小贷中风险命中次数',
	sl_cell_nbank_sloan_overdue_allnum varchar(10) comment '通过手机号查询非银-持牌小贷一般风险命中次数',
	sl_cell_nbank_sloan_fraud_allnum varchar(10) comment '通过手机号查询非银-持牌小贷资信不佳命中次数',
	sl_cell_nbank_sloan_lost_allnum varchar(10) comment '通过手机号查询非银-持牌小贷高风险命中次数',
	sl_cell_nbank_sloan_refuse_allnum varchar(10) comment '通过手机号查询非银-持牌小贷拒绝命中次数',
	sl_cell_nbank_cons_bad_allnum varchar(10) comment '通过手机号查询非银-持牌消费金融中风险命中次数',
	sl_cell_nbank_cons_overdue_allnum varchar(10) comment '通过手机号查询非银-持牌消费金融一般风险命中次数',
	sl_cell_nbank_cons_fraud_allnum varchar(10) comment '通过手机号查询非银-持牌消费金融资信不佳命中次数',
	sl_cell_nbank_cons_lost_allnum varchar(10) comment '通过手机号查询非银-持牌消费金融高风险命中次数',
	sl_cell_nbank_cons_refuse_allnum varchar(10) comment '通过手机号查询非银-持牌消费金融拒绝命中次数',
	sl_cell_nbank_finlea_bad_allnum varchar(10) comment '通过手机号查询非银-持牌融资租赁中风险命中次数',
	sl_cell_nbank_finlea_overdue_allnum varchar(10) comment '通过手机号查询非银-持牌融资租赁一般风险命中次数',
	sl_cell_nbank_finlea_fraud_allnum varchar(10) comment '通过手机号查询非银-持牌融资租赁资信不佳命中次数',
	sl_cell_nbank_finlea_lost_allnum varchar(10) comment '通过手机号查询非银-持牌融资租赁高风险命中次数',
	sl_cell_nbank_finlea_refuse_allnum varchar(10) comment '通过手机号查询非银-持牌融资租赁拒绝命中次数',
	sl_cell_nbank_autofin_bad_allnum varchar(10) comment '通过手机号查询非银-持牌汽车金融中风险命中次数',
	sl_cell_nbank_autofin_overdue_allnum varchar(10) comment '通过手机号查询非银-持牌汽车金融一般风险命中次数',
	sl_cell_nbank_autofin_fraud_allnum varchar(10) comment '通过手机号查询非银-持牌汽车金融资信不佳命中次数',
	sl_cell_nbank_autofin_lost_allnum varchar(10) comment '通过手机号查询非银-持牌汽车金融高风险命中次数',
	sl_cell_nbank_autofin_refuse_allnum varchar(10) comment '通过手机号查询非银-持牌汽车金融拒绝命中次数',
	sl_cell_nbank_other_bad_allnum varchar(10) comment '通过手机号查询非银-其他中风险命中次数',
	sl_cell_nbank_other_overdue_allnum varchar(10) comment '通过手机号查询非银-其他一般风险命中次数',
	sl_cell_nbank_other_fraud_allnum varchar(10) comment '通过手机号查询非银-其他资信不佳命中次数',
	sl_cell_nbank_other_lost_allnum varchar(10) comment '通过手机号查询非银-其他高风险命中次数',
	sl_cell_nbank_other_refuse_allnum varchar(10) comment '通过手机号查询非银-其他拒绝命中次数',
	sl_lm_cell_bank_bad_allnum varchar(10) comment '通过联系人手机查询银行(含信用卡)中风险命中次数',
	sl_lm_cell_bank_overdue_allnum varchar(10) comment '通过联系人手机查询银行(含信用卡)一般风险命中次数',
	sl_lm_cell_bank_fraud_allnum varchar(10) comment '通过联系人手机查询银行(含信用卡)资信不佳命中次数',
	sl_lm_cell_bank_lost_allnum varchar(10) comment '通过联系人手机查询银行(含信用卡)高风险命中次数',
	sl_lm_cell_bank_refuse_allnum varchar(10) comment '通过联系人手机查询银行(含信用卡)拒绝命中次数',
	sl_lm_cell_nbank_bad_allnum varchar(10) comment '通过联系人手机号查询非银(含全部非银类型)中风险命中次数',
	sl_lm_cell_nbank_overdue_allnum varchar(10) comment '通过联系人手机号查询非银(含全部非银类型)一般风险命中次数',
	sl_lm_cell_nbank_fraud_allnum varchar(10) comment '通过联系人手机号查询非银(含全部非银类型)资信不佳命中次数',
	sl_lm_cell_nbank_lost_allnum varchar(10) comment '通过联系人手机号查询非银(含全部非银类型)高风险命中次数',
	sl_lm_cell_nbank_refuse_allnum varchar(10) comment '通过联系人手机号查询非银(含全部非银类型)拒绝命中次数',
	sl_lm_cell_nbank_nsloan_bad_allnum varchar(10) comment '通过联系人手机号查询非银-持牌网络小贷中风险命中次数',
	sl_lm_cell_nbank_nsloan_overdue_allnum varchar(10) comment '通过联系人手机号查询非银-持牌网络小贷一般风险命中次数',
	sl_lm_cell_nbank_nsloan_fraud_allnum varchar(10) comment '通过联系人手机号查询非银-持牌网络小贷资信不佳命中次数',
	sl_lm_cell_nbank_nsloan_lost_allnum varchar(10) comment '通过联系人手机号查询非银-持牌网络小贷高风险命中次数',
	sl_lm_cell_nbank_nsloan_refuse_allnum varchar(10) comment '通过联系人手机号查询非银-持牌网络小贷拒绝命中次数',
	sl_lm_cell_nbank_sloan_bad_allnum varchar(10) comment '通过联系人手机号查询非银-持牌小贷中风险命中次数',
	sl_lm_cell_nbank_sloan_overdue_allnum varchar(10) comment '通过联系人手机号查询非银-持牌小贷一般风险命中次数',
	sl_lm_cell_nbank_sloan_fraud_allnum varchar(10) comment '通过联系人手机号查询非银-持牌小贷资信不佳命中次数',
	sl_lm_cell_nbank_sloan_lost_allnum varchar(10) comment '通过联系人手机号查询非银-持牌小贷高风险命中次数',
	sl_lm_cell_nbank_sloan_refuse_allnum varchar(10) comment '通过联系人手机号查询非银-持牌小贷拒绝命中次数',
	sl_lm_cell_nbank_cons_bad_allnum varchar(10) comment '通过联系人手机号查询非银-持牌消费金融中风险命中次数',
	sl_lm_cell_nbank_cons_overdue_allnum varchar(10) comment '通过联系人手机号查询非银-持牌消费金融一般风险命中次数',
	sl_lm_cell_nbank_cons_fraud_allnum varchar(10) comment '通过联系人手机号查询非银-持牌消费金融资信不佳命中次数',
	sl_lm_cell_nbank_cons_lost_allnum varchar(10) comment '通过联系人手机号查询非银-持牌消费金融高风险命中次数',
	sl_lm_cell_nbank_cons_refuse_allnum varchar(10) comment '通过联系人手机号查询非银-持牌消费金融拒绝命中次数',
	sl_lm_cell_nbank_finlea_bad_allnum varchar(10) comment '通过联系人手机号查询非银-持牌融资租赁中风险命中次数',
	sl_lm_cell_nbank_finlea_overdue_allnum varchar(10) comment '通过联系人手机号查询非银-持牌融资租赁一般风险命中次数',
	sl_lm_cell_nbank_finlea_fraud_allnum varchar(10) comment '通过联系人手机号查询非银-持牌融资租赁资信不佳命中次数',
	sl_lm_cell_nbank_finlea_lost_allnum varchar(10) comment '通过联系人手机号查询非银-持牌融资租赁高风险命中次数',
	sl_lm_cell_nbank_finlea_refuse_allnum varchar(10) comment '通过联系人手机号查询非银-持牌融资租赁拒绝命中次数',
	sl_lm_cell_nbank_autofin_bad_allnum varchar(10) comment '通过联系人手机号查询非银-持牌汽车金融中风险命中次数',
	sl_lm_cell_nbank_autofin_overdue_allnum varchar(10) comment '通过联系人手机号查询非银-持牌汽车金融一般风险命中次数',
	sl_lm_cell_nbank_autofin_fraud_allnum varchar(10) comment '通过联系人手机号查询非银-持牌汽车金融资信不佳命中次数',
	sl_lm_cell_nbank_autofin_lost_allnum varchar(10) comment '通过联系人手机号查询非银-持牌汽车金融高风险命中次数',
	sl_lm_cell_nbank_autofin_refuse_allnum varchar(10) comment '通过联系人手机号查询非银-持牌汽车金融拒绝命中次数',
	sl_lm_cell_nbank_other_bad_allnum varchar(10) comment '通过联系人手机查询非银-其他中风险命中次数',
	sl_lm_cell_nbank_other_overdue_allnum varchar(10) comment '通过联系人手机查询非银-其他一般风险命中次数',
	sl_lm_cell_nbank_other_fraud_allnum varchar(10) comment '通过联系人手机查询非银-其他资信不佳命中次数',
	sl_lm_cell_nbank_other_lost_allnum varchar(10) comment '通过联系人手机查询非银-其他高风险命中次数',
	sl_lm_cell_nbank_other_refuse_allnum varchar(10) comment '通过联系人手机查询非银-其他拒绝命中次数',
	sl_id_court_bad_time varchar(10) comment '通过身份证号查询法院失信人距今时间',
	sl_id_court_executed_time varchar(10) comment '通过身份证号查询法院被执行人距今时间',
	sl_id_bad_info_time varchar(10) comment '通过身份证查询公安信息异常距今时间',
	sl_id_bank_bad_time varchar(10) comment '通过身份证号查询银行(含信用卡)中风险距今时间',
	sl_id_bank_overdue_time varchar(10) comment '通过身份证号查询银行(含信用卡)一般风险距今时间',
	sl_id_bank_fraud_time varchar(10) comment '通过身份证号查询银行(含信用卡)资信不佳距今时间',
	sl_id_bank_lost_time varchar(10) comment '通过身份证号查询银行(含信用卡)高风险距今时间',
	sl_id_bank_refuse_time varchar(10) comment '通过身份证号查询银行(含信用卡)拒绝距今时间',
	sl_id_nbank_bad_time varchar(10) comment '通过身份证号查询非银(含全部非银类型)中风险距今时间',
	sl_id_nbank_overdue_time varchar(10) comment '通过身份证号查询非银(含全部非银类型)一般风险距今时间',
	sl_id_nbank_fraud_time varchar(10) comment '通过身份证号查询非银(含全部非银类型)资信不佳距今时间',
	sl_id_nbank_lost_time varchar(10) comment '通过身份证号查询非银(含全部非银类型)高风险距今时间',
	sl_id_nbank_refuse_time varchar(10) comment '通过身份证号查询非银(含全部非银类型)拒绝距今时间',
	sl_id_nbank_nsloan_bad_time varchar(10) comment '通过身份证号查询非银-持牌网络小贷中风险距今时间',
	sl_id_nbank_nsloan_overdue_time varchar(10) comment '通过身份证号查询非银-持牌网络小贷一般风险距今时间',
	sl_id_nbank_nsloan_fraud_time varchar(10) comment '通过身份证号查询非银-持牌网络小贷资信不佳距今时间',
	sl_id_nbank_nsloan_lost_time varchar(10) comment '通过身份证号查询非银-持牌网络小贷高风险距今时间',
	sl_id_nbank_nsloan_refuse_time varchar(10) comment '通过身份证号查询非银-持牌网络小贷拒绝距今时间',
	sl_id_nbank_sloan_bad_time varchar(10) comment '通过身份证号查询非银-持牌小贷中风险距今时间',
	sl_id_nbank_sloan_overdue_time varchar(10) comment '通过身份证号查询非银-持牌小贷一般风险距今时间',
	sl_id_nbank_sloan_fraud_time varchar(10) comment '通过身份证号查询非银-持牌小贷资信不佳距今时间',
	sl_id_nbank_sloan_lost_time varchar(10) comment '通过身份证号查询非银-持牌小贷高风险距今时间',
	sl_id_nbank_sloan_refuse_time varchar(10) comment '通过身份证号查询非银-持牌小贷拒绝距今时间',
	sl_id_nbank_cons_bad_time varchar(10) comment '通过身份证号查询非银-持牌消费金融中风险距今时间',
	sl_id_nbank_cons_overdue_time varchar(10) comment '通过身份证号查询非银-持牌消费金融一般风险距今时间',
	sl_id_nbank_cons_fraud_time varchar(10) comment '通过身份证号查询非银-持牌消费金融资信不佳距今时间',
	sl_id_nbank_cons_lost_time varchar(10) comment '通过身份证号查询非银-持牌消费金融高风险距今时间',
	sl_id_nbank_cons_refuse_time varchar(10) comment '通过身份证号查询非银-持牌消费金融拒绝距今时间',
	sl_id_nbank_finlea_bad_time varchar(10) comment '通过身份证号查询非银-持牌融资租赁中风险距今时间',
	sl_id_nbank_finlea_overdue_time varchar(10) comment '通过身份证号查询非银-持牌融资租赁一般风险距今时间',
	sl_id_nbank_finlea_fraud_time varchar(10) comment '通过身份证号查询非银-持牌融资租赁资信不佳距今时间',
	sl_id_nbank_finlea_lost_time varchar(10) comment '通过身份证号查询非银-持牌融资租赁高风险距今时间',
	sl_id_nbank_finlea_refuse_time varchar(10) comment '通过身份证号查询非银-持牌融资租赁拒绝距今时间',
	sl_id_nbank_autofin_bad_time varchar(10) comment '通过身份证号查询非银-持牌汽车金融中风险距今时间',
	sl_id_nbank_autofin_overdue_time varchar(10) comment '通过身份证号查询非银-持牌汽车金融一般风险距今时间',
	sl_id_nbank_autofin_fraud_time varchar(10) comment '通过身份证号查询非银-持牌汽车金融资信不佳距今时间',
	sl_id_nbank_autofin_lost_time varchar(10) comment '通过身份证号查询非银-持牌汽车金融高风险距今时间',
	sl_id_nbank_autofin_refuse_time varchar(10) comment '通过身份证号查询非银-持牌汽车金融拒绝距今时间',
	sl_id_nbank_other_bad_time varchar(10) comment '通过身份证号查询非银-其他中风险距今时间',
	sl_id_nbank_other_overdue_time varchar(10) comment '通过身份证号查询非银-其他一般风险距今时间',
	sl_id_nbank_other_fraud_time varchar(10) comment '通过身份证号查询非银-其他资信不佳距今时间',
	sl_id_nbank_other_lost_time varchar(10) comment '通过身份证号查询非银-其他高风险距今时间',
	sl_id_nbank_other_refuse_time varchar(10) comment '通过身份证号查询非银-其他拒绝距今时间',
	sl_cell_bank_bad_time varchar(10) comment '通过手机号查询银行(含信用卡)中风险距今时间',
	sl_cell_bank_overdue_time varchar(10) comment '通过手机号查询银行(含信用卡)一般风险距今时间',
	sl_cell_bank_fraud_time varchar(10) comment '通过手机号查询银行(含信用卡)资信不佳距今时间',
	sl_cell_bank_lost_time varchar(10) comment '通过手机号查询银行(含信用卡)高风险距今时间',
	sl_cell_bank_refuse_time varchar(10) comment '通过手机号查询银行(含信用卡)拒绝距今时间',
	sl_cell_nbank_bad_time varchar(10) comment '通过手机号查询非银(含全部非银类型)中风险距今时间',
	sl_cell_nbank_overdue_time varchar(10) comment '通过手机号查询非银(含全部非银类型)一般风险距今时间',
	sl_cell_nbank_fraud_time varchar(10) comment '通过手机号查询非银(含全部非银类型)资信不佳距今时间',
	sl_cell_nbank_lost_time varchar(10) comment '通过手机号查询非银(含全部非银类型)高风险距今时间',
	sl_cell_nbank_refuse_time varchar(10) comment '通过手机号查询非银(含全部非银类型)拒绝距今时间',
	sl_cell_nbank_nsloan_bad_time varchar(10) comment '通过手机号查询非银-持牌网络小贷中风险距今时间',
	sl_cell_nbank_nsloan_overdue_time varchar(10) comment '通过手机号查询非银-持牌网络小贷一般风险距今时间',
	sl_cell_nbank_nsloan_fraud_time varchar(10) comment '通过手机号查询非银-持牌网络小贷资信不佳距今时间',
	sl_cell_nbank_nsloan_lost_time varchar(10) comment '通过手机号查询非银-持牌网络小贷高风险距今时间',
	sl_cell_nbank_nsloan_refuse_time varchar(10) comment '通过手机号查询非银-持牌网络小贷拒绝距今时间',
	sl_cell_nbank_sloan_bad_time varchar(10) comment '通过手机号查询非银-持牌小贷中风险距今时间',
	sl_cell_nbank_sloan_overdue_time varchar(10) comment '通过手机号查询非银-持牌小贷一般风险距今时间',
	sl_cell_nbank_sloan_fraud_time varchar(10) comment '通过手机号查询非银-持牌小贷资信不佳距今时间',
	sl_cell_nbank_sloan_lost_time varchar(10) comment '通过手机号查询非银-持牌小贷高风险距今时间',
	sl_cell_nbank_sloan_refuse_time varchar(10) comment '通过手机号查询非银-持牌小贷拒绝距今时间',
	sl_cell_nbank_cons_bad_time varchar(10) comment '通过手机号查询非银-持牌消费金融中风险距今时间',
	sl_cell_nbank_cons_overdue_time varchar(10) comment '通过手机号查询非银-持牌消费金融一般风险距今时间',
	sl_cell_nbank_cons_fraud_time varchar(10) comment '通过手机号查询非银-持牌消费金融资信不佳距今时间',
	sl_cell_nbank_cons_lost_time varchar(10) comment '通过手机号查询非银-持牌消费金融高风险距今时间',
	sl_cell_nbank_cons_refuse_time varchar(10) comment '通过手机号查询非银-持牌消费金融拒绝距今时间',
	sl_cell_nbank_finlea_bad_time varchar(10) comment '通过手机号查询非银-持牌融资租赁中风险距今时间',
	sl_cell_nbank_finlea_overdue_time varchar(10) comment '通过手机号查询非银-持牌融资租赁一般风险距今时间',
	sl_cell_nbank_finlea_fraud_time varchar(10) comment '通过手机号查询非银-持牌融资租赁资信不佳距今时间',
	sl_cell_nbank_finlea_lost_time varchar(10) comment '通过手机号查询非银-持牌融资租赁高风险距今时间',
	sl_cell_nbank_finlea_refuse_time varchar(10) comment '通过手机号查询非银-持牌融资租赁拒绝距今时间',
	sl_cell_nbank_autofin_bad_time varchar(10) comment '通过手机号查询非银-持牌汽车金融中风险距今时间',
	sl_cell_nbank_autofin_overdue_time varchar(10) comment '通过手机号查询非银-持牌汽车金融一般风险距今时间',
	sl_cell_nbank_autofin_fraud_time varchar(10) comment '通过手机号查询非银-持牌汽车金融资信不佳距今时间',
	sl_cell_nbank_autofin_lost_time varchar(10) comment '通过手机号查询非银-持牌汽车金融高风险距今时间',
	sl_cell_nbank_autofin_refuse_time varchar(10) comment '通过手机号查询非银-持牌汽车金融拒绝距今时间',
	sl_cell_nbank_other_bad_time varchar(10) comment '通过手机号查询非银-其他中风险距今时间',
	sl_cell_nbank_other_overdue_time varchar(10) comment '通过手机号查询非银-其他一般风险距今时间',
	sl_cell_nbank_other_fraud_time varchar(10) comment '通过手机号查询非银-其他资信不佳距今时间',
	sl_cell_nbank_other_lost_time varchar(10) comment '通过手机号查询非银-其他高风险距今时间',
	sl_cell_nbank_other_refuse_time varchar(10) comment '通过手机号查询非银-其他拒绝距今时间',
	sl_lm_cell_bank_bad_time varchar(10) comment '通过联系人手机查询银行(含信用卡)中风险距今时间',
	sl_lm_cell_bank_overdue_time varchar(10) comment '通过联系人手机查询银行(含信用卡)一般风险距今时间',
	sl_lm_cell_bank_fraud_time varchar(10) comment '通过联系人手机查询银行(含信用卡)资信不佳距今时间',
	sl_lm_cell_bank_lost_time varchar(10) comment '通过联系人手机查询银行(含信用卡)高风险距今时间',
	sl_lm_cell_bank_refuse_time varchar(10) comment '通过联系人手机查询银行(含信用卡)拒绝距今时间',
	sl_lm_cell_nbank_bad_time varchar(10) comment '通过联系人手机号查询非银(含全部非银类型)中风险距今时间',
	sl_lm_cell_nbank_overdue_time varchar(10) comment '通过联系人手机号查询非银(含全部非银类型)一般风险距今时间',
	sl_lm_cell_nbank_fraud_time varchar(10) comment '通过联系人手机号查询非银(含全部非银类型)资信不佳距今时间',
	sl_lm_cell_nbank_lost_time varchar(10) comment '通过联系人手机号查询非银(含全部非银类型)高风险距今时间',
	sl_lm_cell_nbank_refuse_time varchar(10) comment '通过联系人手机号查询非银(含全部非银类型)拒绝距今时间',
	sl_lm_cell_nbank_nsloan_bad_time varchar(10) comment '通过联系人手机号查询非银-持牌网络小贷中风险距今时间',
	sl_lm_cell_nbank_nsloan_overdue_time varchar(10) comment '通过联系人手机号查询非银-持牌网络小贷一般风险距今时间',
	sl_lm_cell_nbank_nsloan_fraud_time varchar(10) comment '通过联系人手机号查询非银-持牌网络小贷资信不佳距今时间',
	sl_lm_cell_nbank_nsloan_lost_time varchar(10) comment '通过联系人手机号查询非银-持牌网络小贷高风险距今时间',
	sl_lm_cell_nbank_nsloan_refuse_time varchar(10) comment '通过联系人手机号查询非银-持牌网络小贷拒绝距今时间',
	sl_lm_cell_nbank_sloan_bad_time varchar(10) comment '通过联系人手机号查询非银-持牌小贷中风险距今时间',
	sl_lm_cell_nbank_sloan_overdue_time varchar(10) comment '通过联系人手机号查询非银-持牌小贷一般风险距今时间',
	sl_lm_cell_nbank_sloan_fraud_time varchar(10) comment '通过联系人手机号查询非银-持牌小贷资信不佳距今时间',
	sl_lm_cell_nbank_sloan_lost_time varchar(10) comment '通过联系人手机号查询非银-持牌小贷高风险距今时间',
	sl_lm_cell_nbank_sloan_refuse_time varchar(10) comment '通过联系人手机号查询非银-持牌小贷拒绝距今时间',
	sl_lm_cell_nbank_cons_bad_time varchar(10) comment '通过联系人手机号查询非银-持牌消费金融中风险距今时间',
	sl_lm_cell_nbank_cons_overdue_time varchar(10) comment '通过联系人手机号查询非银-持牌消费金融一般风险距今时间',
	sl_lm_cell_nbank_cons_fraud_time varchar(10) comment '通过联系人手机号查询非银-持牌消费金融资信不佳距今时间',
	sl_lm_cell_nbank_cons_lost_time varchar(10) comment '通过联系人手机号查询非银-持牌消费金融高风险距今时间',
	sl_lm_cell_nbank_cons_refuse_time varchar(10) comment '通过联系人手机号查询非银-持牌消费金融拒绝距今时间',
	sl_lm_cell_nbank_finlea_bad_time varchar(10) comment '通过联系人手机号查询非银-持牌融资租赁中风险距今时间',
	sl_lm_cell_nbank_finlea_overdue_time varchar(10) comment '通过联系人手机号查询非银-持牌融资租赁一般风险距今时间',
	sl_lm_cell_nbank_finlea_fraud_time varchar(10) comment '通过联系人手机号查询非银-持牌融资租赁资信不佳距今时间',
	sl_lm_cell_nbank_finlea_lost_time varchar(10) comment '通过联系人手机号查询非银-持牌融资租赁高风险距今时间',
	sl_lm_cell_nbank_finlea_refuse_time varchar(10) comment '通过联系人手机号查询非银-持牌融资租赁拒绝距今时间',
	sl_lm_cell_nbank_autofin_bad_time varchar(10) comment '通过联系人手机号查询非银-持牌汽车金融中风险距今时间',
	sl_lm_cell_nbank_autofin_overdue_time varchar(10) comment '通过联系人手机号查询非银-持牌汽车金融一般风险距今时间',
	sl_lm_cell_nbank_autofin_fraud_time varchar(10) comment '通过联系人手机号查询非银-持牌汽车金融资信不佳距今时间',
	sl_lm_cell_nbank_autofin_lost_time varchar(10) comment '通过联系人手机号查询非银-持牌汽车金融高风险距今时间',
	sl_lm_cell_nbank_autofin_refuse_time varchar(10) comment '通过联系人手机号查询非银-持牌汽车金融拒绝距今时间',
	sl_lm_cell_nbank_other_bad_time varchar(10) comment '通过联系人手机查询非银-其他中风险距今时间',
	sl_lm_cell_nbank_other_overdue_time varchar(10) comment '通过联系人手机查询非银-其他一般风险距今时间',
	sl_lm_cell_nbank_other_fraud_time varchar(10) comment '通过联系人手机查询非银-其他资信不佳距今时间',
	sl_lm_cell_nbank_other_lost_time varchar(10) comment '通过联系人手机查询非银-其他高风险距今时间',
	sl_lm_cell_nbank_other_refuse_time varchar(10) comment '通过联系人手机查询非银-其他拒绝距今时间'
   PRIMARY KEY (batch_id, phone_number)
)
ENGINE InnoDB;