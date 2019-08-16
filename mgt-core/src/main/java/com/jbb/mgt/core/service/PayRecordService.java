package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.PayRecord;

public interface PayRecordService {

    // 插入代付记录
    void insertPayRecord(PayRecord payRecord);

    // 修改状态
    void updatePayRecord(String orderId, String orderStatus,String reason);

    // 根据订单号查询
    PayRecord selectByOrderId(String orderId);

    // 根据流水号查询
    PayRecord selectBySerialNumber(String serialNumber);

    // 申请借款applyId
    PayRecord selectByApplyId(String applyId);

    String[] selectUnfinalRecord(int size, int orgId);
}
