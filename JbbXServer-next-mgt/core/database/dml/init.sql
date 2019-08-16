-- ----------------------------
-- Records  company_introduction
-- ----------------------------
INSERT INTO `company_introduction` VALUES ('1', '借帮帮', '借帮帮，帮有序借款，避免让自己陷入债务危机。', '借帮帮是一个帮助用户进行债务记账的智能信用管理平台。 商务合作：29803269@qq.com', '285094', '2018-01-02 14:53:48');


-- ----------------------------
-- loan_types
-- ----------------------------
INSERT INTO `loan_types`(loan_type_id, name, description) VALUES ('1', '一次性偿本付息', '一次性偿本付息');


-- ----------------------------
-- advertising 
-- ----------------------------
INSERT INTO `advertising` VALUES ('1', 'Web', '借帮帮', 'jbb-web-a.png', '借帮帮', '1', 'apk006/app-anzhi-release_legu_signed_zipalign.apk', '2018-01-20 00:00:00');
INSERT INTO `advertising` VALUES ('2', 'Web', '借帮帮', 'jbb-web-2.png', '新浪你好', '2', 'apk006/app-anzhi-release_legu_signed_zipalign.apk', '2018-01-20 00:00:00');
INSERT INTO `advertising` VALUES ('3', 'IOS', '借帮帮', 'jbb-app-1.jpg', '借帮帮', '1', null, '2018-01-20 00:00:00');
INSERT INTO `advertising` VALUES ('4', 'IOS', '借帮帮', 'jbb-app-2.jpg', '借帮帮', '2', null, '2018-01-20 00:00:00');
INSERT INTO `advertising` VALUES ('5', 'Android', '借帮帮', 'jbb-app-1.jpg', '借帮帮', '1', null, '2018-01-20 00:00:00');
INSERT INTO `advertising` VALUES ('6', 'Android', '借帮帮', 'jbb-app-2.jpg', '借帮帮', '2', null, '2018-01-20 00:00:00');

-- ----------------------------
-- PROD, POST数据至出借方，服务器配置
-- ----------------------------
INSERT INTO user_properties (user_id, name, value) VALUES (1001963,'postDateClassName','cyPushUserService');
INSERT INTO user_properties (user_id, name, value) VALUES (1001963,'postDateServerPath','http://139.196.121.68:8800/channel/push');