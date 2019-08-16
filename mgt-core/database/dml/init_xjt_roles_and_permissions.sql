-- permissions
INSERT INTO mgt_permissions (permission_id, description) VALUES (202, '系统管理员');
INSERT INTO mgt_permissions (permission_id, description) VALUES (203, '充值');
INSERT INTO mgt_permissions (permission_id, description) VALUES (204, '账户设置');
INSERT INTO mgt_permissions (permission_id, description) VALUES (205, '渠道管理');
INSERT INTO mgt_permissions (permission_id, description) VALUES (206, '待领取');
INSERT INTO mgt_permissions (permission_id, description) VALUES (207, '审核待领取');
INSERT INTO mgt_permissions (permission_id, description) VALUES (208, '催收待领取');
INSERT INTO mgt_permissions (permission_id, description) VALUES (209, '订单管理');
INSERT INTO mgt_permissions (permission_id, description) VALUES (210, '已申请借款');
INSERT INTO mgt_permissions (permission_id, description) VALUES (211, '未申请借款');
INSERT INTO mgt_permissions (permission_id, description) VALUES (212, '业务报表');

-- roles
INSERT INTO mgt_roles (role_id, description, application_id) VALUES (202, '系统管理员', 2);
INSERT INTO mgt_roles (role_id, description, application_id) VALUES (203, '市场员', 2);
INSERT INTO mgt_roles (role_id, description, application_id) VALUES (204, '审核员', 2);
INSERT INTO mgt_roles (role_id, description, application_id) VALUES (205, '审核主管', 2);
INSERT INTO mgt_roles (role_id, description, application_id) VALUES (206, '客服', 2);
INSERT INTO mgt_roles (role_id, description, application_id) VALUES (207, '催告员', 2);


-- role permissions 
-- 系统管理员
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (202, 202);
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (202, 212);

-- 市场员
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (203, 205);

-- 审核员
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (204, 207);
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (204, 209);

-- 催告员
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (207, 208);
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (207, 209);

-- 审核主管
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (205, 210);

-- 客服
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (206, 206);
INSERT INTO mgt_role_permissions (role_id, permission_id) VALUES (206, 211);