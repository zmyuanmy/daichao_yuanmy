package com.jbb.mgt.core.service;

import com.alibaba.fastjson.JSONObject;
import com.jbb.mgt.core.domain.User;

import java.sql.Timestamp;

public interface QianChengService {

    String getToken();

    String getVerifyResult(User user, String applyId);

    /**
     * 组件用户调用风控审核接口的参数
     * 
     * @return
     */
    JSONObject getVerifyData(User user, Timestamp startDate,Timestamp endDate);

    void checkUserApplyRecord();

}
