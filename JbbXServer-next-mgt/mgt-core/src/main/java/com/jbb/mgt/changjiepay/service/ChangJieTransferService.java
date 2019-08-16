package com.jbb.mgt.changjiepay.service;

import com.jbb.mgt.core.domain.XjlApplyRecord;

public interface ChangJieTransferService {

    // 商户余额查询 cjt_dsf
    void queryBalance(String acctNo,String acctName);

    // 异步单笔代付 cjt_dsf
    void transfer(String cardNo,String userName,String amount,String bankCode,String applyId,int accountId,XjlApplyRecord xApplyRecord);

    // 单笔交易查询
    void transferQuery(String orderId);
}
