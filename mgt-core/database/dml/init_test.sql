
insert into mgt_organizations(org_id, name, description) values (1, 'JBB TEST ORG', 'JBB test');

insert into `jbbserver`.`mgt_accounts` ( `account_id`, `username`,`nickname`, `phone_number`, `jbb_user_id`, `password`, `org_id`, `creator`, `creation_date`) values (1, 'test org admin','test org admin', '13510000001', null, null, '1', null, '2018-04-20 19:40:25.285');
insert into `jbbserver`.`mgt_accounts` ( `account_id`,`username`, `nickname`,`phone_number`, `jbb_user_id`, `password`, `org_id`, `creator`, `creation_date`) values (2, 'test2','test2', '13510000002', null, null, '1', '1', '2018-04-20 19:40:46.815');
insert into `jbbserver`.`mgt_accounts` ( `account_id`,`username`, `nickname`,`phone_number`, `jbb_user_id`, `password`, `org_id`, `creator`, `creation_date`) values (3, 'test3','test3', '13510000003', null, null, '1', '1', '2018-04-20 19:40:57.377');
insert into `jbbserver`.`mgt_accounts` ( `account_id`,`username`, `nickname`,`phone_number`, `jbb_user_id`, `password`, `org_id`, `creator`, `creation_date`) values (4, 'test4', 'test4', '13510000004', null, null, '1', '1', '2018-04-20 19:41:10.445');

insert into mgt_account_roles(account_id, role_id) values (1, 2);
insert into mgt_account_roles(account_id, role_id) values (2, 3);
insert into mgt_account_roles(account_id, role_id) values (3, 4);
insert into mgt_account_roles(account_id, role_id) values (4, 5);

insert into mgt_users(user_id, username, phone_number, idcard) values (20, '邓露','17673110274', '430122199606214527');
insert into mgt_user_apply_records(apply_id, user_id, org_id) values(1,20,1);
insert into mgt_users(user_id, username, phone_number, idcard) values (1, '邓露','17673110274', '430122199606214527');


-- orgs
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('11','testOrg1','testOrg2','0','30','【借帮帮】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('100003','testOgr1Admin','','','11','1','TestOrgAdmin1'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('100003','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('11','10000','10000','0','0');       

INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('12','testOrg2','testOrg2','0','30','【借帮帮】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('100004','testOgr2Admin','','','12','1','TestOrgAdmin2'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('100004','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('12','10000','10000','0','0');
       
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('13','testOrg3','testOrg3','0','30','【借帮帮】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('100005','testOgr3Admin','','','13','1','TestOrgAdmin2'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('100005','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('13','10000','10000','0','0');
       
       
insert into `jbbservertest`.`mgt_account_dflow_settings` ( `org_id`, `is_closed`, `dflow_id`, `min_value`, `max_value`) values ( '11', '0', '1', '300', '500');
insert into `jbbservertest`.`mgt_account_dflow_settings` ( `org_id`, `is_closed`, `dflow_id`, `min_value`, `max_value`) values ( '12', '0', '2', '300', '500');
insert into `jbbservertest`.`mgt_account_dflow_settings` ( `org_id`, `is_closed`, `dflow_id`, `min_value`, `max_value`) values ( '13', '0', '3', '300', '500');

       
       