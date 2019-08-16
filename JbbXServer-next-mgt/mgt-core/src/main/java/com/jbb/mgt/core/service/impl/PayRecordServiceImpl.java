package com.jbb.mgt.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.PayRecordDao;
import com.jbb.mgt.core.domain.PayRecord;
import com.jbb.mgt.core.service.PayRecordService;

@Service("PayRecordService")
public class PayRecordServiceImpl implements PayRecordService {

    @Autowired
    private PayRecordDao payRecordDao;

    @Override
    public void insertPayRecord(PayRecord payRecord) {
        payRecordDao.insertPayRecord(payRecord);
    }

    @Override
    public void updatePayRecord(String orderId, String orderStatus, String retReason) {
        payRecordDao.updatePayRecord(orderId, orderStatus, retReason);
    }

    @Override
    public PayRecord selectByOrderId(String orderId) {
        return payRecordDao.selectByOrderId(orderId);
    }

    @Override
    public PayRecord selectBySerialNumber(String serialNumber) {
        return payRecordDao.selectBySerialNumber(serialNumber);
    }

    @Override
    public PayRecord selectByApplyId(String applyId) {
        return payRecordDao.selectByApplyId(applyId);
    }

    @Override
    public String[] selectUnfinalRecord(int size, int orgId) {
        return payRecordDao.selectUnfinalRecord(size, orgId);
    }

}
