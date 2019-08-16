-- permissions
insert into mgt_permissions (permission_id, description) values (101,'系统管理员');
insert into mgt_permissions (permission_id, description) values (102,'组织管理员');
insert into mgt_permissions (permission_id, description) values (103,'渠道推广');
insert into mgt_permissions (permission_id, description) values (104,'H5商家管理');
insert into mgt_permissions (permission_id, description) values (105,'组织统计数据列表');
insert into mgt_permissions (permission_id, description) values (106,'添加组织');
insert into mgt_permissions (permission_id, description) values (107,'进件 - 其他');
insert into mgt_permissions (permission_id, description) values (108,'进件 - 推荐');
insert into mgt_permissions (permission_id, description) values (109,'非纯进件');
insert into mgt_permissions (permission_id, description) values (110,'渠道扣量');
insert into mgt_permissions (permission_id, description) values (111,'总报表');
insert into mgt_permissions (permission_id, description) values (112,'注册导流报表');
insert into mgt_permissions (permission_id, description) values (113,'进件导流报表 ');
insert into mgt_permissions (permission_id, description) values (114,'代理导流报表');
insert into mgt_permissions (permission_id, description) values (115,'注册导流充消报表');
insert into mgt_permissions (permission_id, description) values (116,'进件导流充消报表');
insert into mgt_permissions (permission_id, description) values (117,'代理充消报表');
insert into mgt_permissions (permission_id, description) values (118,'H5客户充消报表');
insert into mgt_permissions (permission_id, description) values (119,'贷超充消报表');
insert into mgt_permissions (permission_id, description) values (120,'渠道充消报表');
insert into mgt_permissions (permission_id, description) values (121,'注册模式漏斗报表 ');
insert into mgt_permissions (permission_id, description) values (122,'进件模式漏斗报表 ');
insert into mgt_permissions (permission_id, description) values (123,'H5商家业务数据表');
insert into mgt_permissions (permission_id, description) values (124,'贷超业务数据表');
insert into mgt_permissions (permission_id, description) values (125,'贷超渠道绩效表');
insert into mgt_permissions (permission_id, description) values (126,'渠道总体情况表');
insert into mgt_permissions (permission_id, description) values (127,'注册模式渠道质量反馈表');
insert into mgt_permissions (permission_id, description) values (128,'进件模式渠道质量反馈表');
insert into mgt_permissions (permission_id, description) values (129,'平台链接 ');
insert into mgt_permissions (permission_id, description) values (130,'app区域配置');
insert into mgt_permissions (permission_id, description) values (131,'平台配置');
insert into mgt_permissions (permission_id, description) values (133,'贷超产品数据上报及修改');
insert into mgt_permissions (permission_id, description) values (136,'渠道数据管理(渠道冲销修改和确认)');
insert into mgt_permissions (permission_id, description) values (137,'财务（贷超产品收款数据修改和确认）');

--101	系统管理员
--102	组织管理员
--107	贷超管理员
--103	渠道
--104	运营岗1
--105	运营岗2
--109	运营岗3
--106	商务
--108	财务
-- roles
insert into mgt_roles (role_id, description,application_id) values (101, '运营子系统系统管理员',1);
insert into mgt_roles (role_id, description,application_id) values (102, '运营子系统系统组织管理员',1);
insert into mgt_roles (role_id, description,application_id) values (103, '销售',1);
insert into mgt_roles (role_id, description,application_id) values (104, '综合运营',1);
insert into mgt_roles (role_id, description,application_id) values (105, '产品运营',1);
insert into mgt_roles (role_id, description,application_id) values (106, '商务',1);
insert into mgt_roles (role_id, description,application_id) values (107, '贷超管理员',1);
insert into mgt_roles (role_id, description,application_id) values (108, '财务',1);
insert into mgt_roles (role_id, description,application_id) values (109, '运营岗3',1);

-- role permissions 
insert into mgt_role_permissions (role_id, permission_id) values (101, 101);
insert into mgt_role_permissions (role_id, permission_id) values (102, 102);
-- 渠道 -103
insert into mgt_role_permissions (permission_id, role_id) values (103, 103);
insert into mgt_role_permissions (permission_id, role_id) values (120, 103);
insert into mgt_role_permissions (permission_id, role_id) values (124, 103);
insert into mgt_role_permissions (permission_id, role_id) values (125, 103);

-- 运营岗1 104
insert into mgt_role_permissions (permission_id, role_id) values (103, 104);
insert into mgt_role_permissions (permission_id, role_id) values (110, 104);
insert into mgt_role_permissions (permission_id, role_id) values (111, 104);
insert into mgt_role_permissions (permission_id, role_id) values (119, 104);
insert into mgt_role_permissions (permission_id, role_id) values (120, 104);
insert into mgt_role_permissions (permission_id, role_id) values (124, 104);
insert into mgt_role_permissions (permission_id, role_id) values (125, 104);
insert into mgt_role_permissions (permission_id, role_id) values (129, 104);
insert into mgt_role_permissions (permission_id, role_id) values (130, 104);
insert into mgt_role_permissions (permission_id, role_id) values (131, 104);
insert into mgt_role_permissions (permission_id, role_id) values (133, 104);
insert into mgt_role_permissions (permission_id, role_id) values (136, 104);

-- 运营岗2 105
insert into mgt_role_permissions (permission_id, role_id) values (103, 105);
insert into mgt_role_permissions (permission_id, role_id) values (110, 105);
insert into mgt_role_permissions (permission_id, role_id) values (119, 105);
insert into mgt_role_permissions (permission_id, role_id) values (120, 105);
insert into mgt_role_permissions (permission_id, role_id) values (124, 105);
insert into mgt_role_permissions (permission_id, role_id) values (125, 105);
insert into mgt_role_permissions (permission_id, role_id) values (129, 105);
insert into mgt_role_permissions (permission_id, role_id) values (130, 105);
insert into mgt_role_permissions (permission_id, role_id) values (131, 105);
insert into mgt_role_permissions (permission_id, role_id) values (133, 105);

-- 商务 106
insert into mgt_role_permissions (permission_id, role_id) values (119, 106);
insert into mgt_role_permissions (permission_id, role_id) values (124, 106);
insert into mgt_role_permissions (permission_id, role_id) values (133, 106);
insert into mgt_role_permissions (permission_id, role_id) values (136, 106);

-- 贷超管理员 107
insert into mgt_role_permissions (permission_id, role_id) values (103, 107);
insert into mgt_role_permissions (permission_id, role_id) values (110, 107);
insert into mgt_role_permissions (permission_id, role_id) values (111, 107);
insert into mgt_role_permissions (permission_id, role_id) values (119, 107);
insert into mgt_role_permissions (permission_id, role_id) values (120, 107);
insert into mgt_role_permissions (permission_id, role_id) values (124, 107);
insert into mgt_role_permissions (permission_id, role_id) values (125, 107);
insert into mgt_role_permissions (permission_id, role_id) values (129, 107);
insert into mgt_role_permissions (permission_id, role_id) values (130, 107);
insert into mgt_role_permissions (permission_id, role_id) values (131, 107);
insert into mgt_role_permissions (permission_id, role_id) values (133, 107);
insert into mgt_role_permissions (permission_id, role_id) values (136, 107);

--108	财务
insert into mgt_role_permissions (permission_id, role_id) values (111, 108);
insert into mgt_role_permissions (permission_id, role_id) values (119, 108);
insert into mgt_role_permissions (permission_id, role_id) values (120, 108);
insert into mgt_role_permissions (permission_id, role_id) values (136, 108);
insert into mgt_role_permissions (permission_id, role_id) values (137, 108);

-- 109 运营岗3
insert into mgt_role_permissions (permission_id, role_id) values (120, 109);



insert mgt_account_roles(account_id, role_id) values(1,101);
insert mgt_account_roles(account_id, role_id) values(1,102);
insert mgt_account_roles(account_id, role_id) values(10252,102);
insert mgt_account_roles(account_id, role_id) values(10365,102);
insert mgt_account_roles(account_id, role_id) values(10677,102);

insert mgt_account_roles(account_id, role_id) values(12235,107);
insert mgt_account_roles(account_id, role_id) values(11125,103);
insert mgt_account_roles(account_id, role_id) values(11405,103);
insert mgt_account_roles(account_id, role_id) values(10251,104);
insert mgt_account_roles(account_id, role_id) values(10625,104);
insert mgt_account_roles(account_id, role_id) values(11364,105);
insert mgt_account_roles(account_id, role_id) values(11387,109);
insert mgt_account_roles(account_id, role_id) values(11744,108);
insert mgt_account_roles(account_id, role_id) values(12414,106);
insert mgt_account_roles(account_id, role_id) values(12415,106);
insert mgt_account_roles(account_id, role_id) values(10690,106);
insert mgt_account_roles(account_id, role_id) values(10250,106);
insert mgt_account_roles(account_id, role_id) values(10363,106);
