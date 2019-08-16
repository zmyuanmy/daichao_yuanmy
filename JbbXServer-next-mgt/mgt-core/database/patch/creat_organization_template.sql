-- 0608
/*==============================================================*/
/* 创建组织  103 陆朝晖  瀚铂钱庄 1141775*/
/*==============================================================*/
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('103','瀚铂钱庄','瀚铂钱庄','0','30','【借帮帮】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10005','haobo','','1141775','103','1','陆朝晖'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10005','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('103','10000','10000','0','0');
       
 
-- 0609
/*==============================================================*/
/* 创建组织  104 朱新宇  贷贷乐   1095681*/
/*==============================================================*/       
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('104','贷贷乐','贷贷乐','0','30','【借帮帮】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10006','Daidaile','','1095681','104','1','朱新宇'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10006','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('104','10000','10000','0','0');
       

/*==============================================================*/
/* 创建组织  105 张良  世纪金融   1148407  shiji123*/
/*==============================================================*/       
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('105','世纪金融','世纪金融','0','30','【借帮帮】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10009','shiji123','','1148407','105','1','张良'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10009','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('105','300000','0','0','0');
       
 
/*==============================================================*/
/* 创建组织  106 凌凯辉  红领贷   1148572  honglingdai*/
/*==============================================================*/       
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('106','红领贷','红领贷','0','30','【借帮帮】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10010','honglingdai','','1148572','106','1','凌凯辉'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10010','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('106','100000','0','0','0');
       
       
-- 组织名：易速金融
-- 管理员：吴智忠
-- 帮帮ID：1148579
-- 账号：yisujinrong
-- 密码：12345678
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('107','易速金融','易速金融','0','30','【易速金融】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10011','yisujinrong','','1148579','107','1','吴智忠'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10011','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('107','0','0','0','0');
       
--组织名：金钱钱包
--管理员：金贤定
--帮帮ID：1094002
--账号：jinqianbao
--密码：12345678
             
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('108','金钱钱包','金钱钱包','0','30','【金钱钱包】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10014','jinqianbao','','1094002','108','1','金贤定'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10014','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('108','0','0','0','0');
--组织名：金米助手
--管理员：柯福达
--帮帮ID：1149436
--登录用户名：13063077788
--密码：12345678
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('109','金米助手','金米助手','0','30','【金米助手】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10016','13063077788','','1149436','109','1','柯福达'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10016','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('109','10000','10000','0','0');
--管理员：陶鹏程
--帮帮ID：1150541
--组织名：哲翰金融
--登陆用户名：zhehanjinrong
--密码：12345678    
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('110','哲翰金融','哲翰金融','0','30','【哲翰金融】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10018','zhehanjinrong','','1150541','110','1','陶鹏程'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10018','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('110','0','0','0','0');
--组织名：优米钱包
--管理员：周全景
--帮帮ID：1154796
--账号：zqj1123
--密码：12345678
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('111','优米钱包','优米钱包','0','30','【优米钱包】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10022','zqj1123','','1154796','111','1','周全景'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10022','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('111','0','0','0','0');
--组织名：借花花
--管理员：张锋
--帮帮ID：1155454
--账号：jiehuahua
--密码：12345678
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('112','借花花','借花花','0','30','【借花花】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10023','jiehuahua','','1155454','112','1','张锋'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10023','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('112','0','0','0','0');

--组织名：日晟速借
--管理员：孙善龙
--帮帮ID：1159605
--登录账号：risheng
--密码：12345678
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('113','日晟速借','日晟速借','0','30','【日晟速借】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10026','risheng','','1159605','113','1','孙善龙'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10026','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('10026','0','0','0','0');

--帮帮ID：1169341
--姓名：林武
--商户名字：诚泰金融
--账号：chengtaijinrong
--密码：12345678
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('114','诚泰金融','诚泰金融','0','30','【诚泰金融】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10033','chengtaijinrong','','1169341','114','1','林武'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10033','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('114','0','0','0','0');

--帮帮ID：1174880
--姓名：朱城
--商户名字：朱城
--账号：zhucheng
--密码：12345678
INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('115','朱城','朱城','0','30','【朱城】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10034','zhucheng','','1174880','115','1','朱城'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10034','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('115','0','0','0','0');
--帮ID： 1183103
--姓名：黄华晓
--商户名字：金融钱袋
--账号:jinrongqiandai
--密码：12345678     
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('116','金融钱袋','金融钱袋','0','30','【金融钱袋】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10035','jinrongqiandai','','1183103','116','1','黄华晓'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10035','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('116','0','0','0','0');
--帮帮ID：1191341
--商户名称：芝麻借条
--名字：彭天宇
--账号：zhimajietiao
--密码：12345678
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('117','芝麻借条','芝麻借条','0','30','【芝麻借条】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10039','zhimajietiao','','1191341','117','1','彭天宇'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10039','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('117','0','0','0','0')
--真实姓名：钱钠  
--帮帮ID：1195971
--商户名字：旺利金融
--账号：wanglijinrong
--密码:12345678
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('119','旺利金融','旺利金融','0','30','【旺利金融】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10045','wanglijinrong','','1195971','119','1','钱钠'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10045','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('119','0','0','0','0');
--帮帮ID： 1137666
--商户名字：三月金融
--真实名字：靳悦鹏
--账号：sanyuejinrong
--密码： 12345678
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('120','三月金融','三月金融','0','30','【三月金融】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10047','sanyuejinrong','','1137666','120','1','靳悦鹏'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10047','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('120','0','0','0','0');
--帮帮ID： 1124036
--商户名字：融信贷
--真实名字：江宁
--账号：rongxindai
--密码： 12345678
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('121','融信贷','融信贷','0','30','【融信贷】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10050','rongxindai','','1124036','121','1','江宁'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10050','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('121','0','0','0','0');
--帮帮ID： 1202707
--商户名字：汇通
--真实名字：黄春荣
--账号：huitong
--密码： 12345678    
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('122','汇通','汇通','0','30','【汇通】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10053','huitong','','1202707','122','1','黄春荣'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10053','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('122','0','0','0','0');

--组织名：金驰好贷
--管理员：金志明
--帮帮ID：1216399
--账号：jinchi123
--密码：12345678
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('123','金驰好贷','金驰好贷','0','30','【金驰好贷】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10060','jinchi123','','1216399','123','1','金志明'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10060','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('123','0','0','0','0');
       
--帮帮ID：1222938
--商户名字：完美
--真实名字：周浩伟
--账号：17318322232
--密码： 12345678
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('124','完美','完美','0','30','【完美】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10067','17318322232','','1222938','124','1','周浩伟'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10067','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('124','0','0','0','0');
       
--帮帮ID：1223364
--商户名字：小米金融
--真实名字：王健
--账号：xiaomijinrong
--密码： 12345678
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('125','小米金融','小米金融','0','30','【小米金融】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10068','xiaomijinrong','','1223364','125','1','王健'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10068','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('125','0','0','0','0');
--帮帮ID：1219681
--商户名字：五岳金融
--真实名字：姜启超
--账号：wuyuejinrong
--密码： 12345678
     
INSERT INTO mgt_organizations(org_id,name,description,deleted,count,sms_sign_name,sms_template_id)
       VALUES('126','五岳金融','五岳金融','0','30','【五岳金融】','SMS_121165611');
INSERT INTO mgt_accounts(account_id,username,phone_number,jbb_user_id,org_id,creator,nickname,password)
       VALUES('10069','wuyuejinrong','','1219681','126','1','姜启超'
              ,'$argon2d$v=19$m=512,t=2048,p=1$1CChY7mmA4vWrAggvMVNag$gmv3wwi53WF7YcQ8/IulSMij11uUrYUebogxiGNW6W8');
INSERT INTO mgt_account_roles(account_id,role_id)VALUES('10069','2');
INSERT INTO mgt_org_recharges (org_id,total_data_amount,total_sms_amount,total_data_expense,total_sms_expense)
       VALUES('126','0','0','0','0');