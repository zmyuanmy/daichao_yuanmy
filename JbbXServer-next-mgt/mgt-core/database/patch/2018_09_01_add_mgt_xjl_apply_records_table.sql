alter table mgt_xjl_apply_records add column open_id varchar(100) comment '风控接口的openId';
alter table mgt_xjl_apply_records add column notify_status varchar(10) comment '回调状态，null表示未回调过,1表示已经回调过';
alter table mgt_xjl_apply_records add column apply_status varchar(10) comment '审核状态，null表示未提交审核，1表示已提交审核，2表示拒绝，100表示通过审核';
alter table mgt_xjl_apply_records add column apply_msg varchar(100) comment '风控接口的审核返回的信息,资质不符/逾期过多/通过等';