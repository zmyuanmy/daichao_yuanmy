package com.jbb.boss.service;

import com.jbb.mgt.core.domain.User;

public interface BossService {

    // 通讯录信息回流
    void backFlowAddressBook(User user) throws Exception;

    // 短信信息回流
    void backFlowMessage(User user) throws Exception;

    // App安装列表回流
    void backFlowAppInfo(User user) throws Exception;

    // 通话记录信息回流
    void backFlowCallLog(User user) throws Exception;

    // 运营商信息回流
    void backFlowOperatorInfo(User user) throws Exception;

    // 请求获取小金条风控评估报告
    void getBossReportRequest(User user) throws Exception;





}
