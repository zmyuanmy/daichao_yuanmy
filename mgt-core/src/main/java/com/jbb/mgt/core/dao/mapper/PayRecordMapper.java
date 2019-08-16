package com.jbb.mgt.core.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.PayRecord;

public interface PayRecordMapper {

    // 插入代付记录
    void insertPayRecord(PayRecord payRecord);

    // 修改状态
    void updatePayRecord(@Param("orderId") String orderId, @Param("orderStatus") String orderStatus,
        @Param("retReason") String retReason);

    // 根据订单号查询
    PayRecord selectByOrderId(@Param("orderId") String orderId);

    // 根据流水号查询
    PayRecord selectBySerialNumber(@Param("serialNumber") String serialNumber);

    // 申请借款applyId
    PayRecord selectByApplyId(@Param("applyId") String applyId);

    String[] selectUnfinalRecord(@Param("size") int size, @Param("orgId") int orgId);
}
