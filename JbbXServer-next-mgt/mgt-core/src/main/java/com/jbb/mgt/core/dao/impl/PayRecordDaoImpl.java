package com.jbb.mgt.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.PayRecordDao;
import com.jbb.mgt.core.dao.mapper.PayRecordMapper;
import com.jbb.mgt.core.domain.PayRecord;

@Repository("PayRecordDao")
public class PayRecordDaoImpl implements PayRecordDao {

    @Autowired
    private PayRecordMapper mapper;

    @Override
    public void insertPayRecord(PayRecord payRecord) {
        mapper.insertPayRecord(payRecord);
    }

    @Override
    public void updatePayRecord(String orderId, String orderStatus, String retReason) {
        mapper.updatePayRecord(orderId, orderStatus, retReason);
    }

    @Override
    public PayRecord selectByOrderId(String orderId) {
        return mapper.selectByOrderId(orderId);
    }

    @Override
    public PayRecord selectBySerialNumber(String serialNumber) {
        return mapper.selectBySerialNumber(serialNumber);
    }

    @Override
    public PayRecord selectByApplyId(String applyId) {
        return mapper.selectByApplyId(applyId);
    }

    @Override
    public String[] selectUnfinalRecord(int size, int orgId) {
        return mapper.selectUnfinalRecord(size, orgId);
    }

}
