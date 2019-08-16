package com.jbb.server.core.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jbb.server.core.dao.BillingDao;
import com.jbb.server.core.dao.mapper.BillingMapper;
import com.jbb.server.core.domain.Billing;
import com.jbb.server.core.domain.BillingDetail;
import com.jbb.server.core.domain.Repayment;

@Repository("BillingDao")
public class BillingDaoImpl implements BillingDao {

    @Autowired
    private BillingMapper mapper;

    @Override
    public void createBilling(Billing billing) {
        mapper.insertBilling(billing);
    }

    @Override
    public void deleteBilling(int billingId) {
        mapper.deleteBilling(billingId);

    }

    @Override
    public void updateBilling(Billing billing) {
        mapper.updateBilling(billing);

    }

    @Override
    public Billing getBilling(int userId, int billingId, boolean detail) {
        return mapper.selectBilling(userId, billingId, detail);

    }

    @Override
    public List<Billing> getBillings(int userId, boolean detail) {
        return mapper.selectBillings(userId, detail);

    }
    
    @Override
    public List<Billing> getBillingsByDate(int userId, Timestamp startDate, Timestamp endDate) {
        return mapper.selectBillingsByDate(userId, startDate, endDate);
    }

    @Override
    public void createBillingDetail(BillingDetail billingDetail) {
        mapper.insertBillingDetail(billingDetail);

    }

    @Override
    public void updateBillingDetail(BillingDetail billingDetail) {
        mapper.updateBillingDetail(billingDetail);

    }

    @Override
    public BillingDetail getBillingDetail(int billingDetailId, boolean detail) {
        return mapper.selectBillingDetail(billingDetailId, detail);

    }

    @Override
    public List<BillingDetail> getBillingDetails(int billingId, boolean detail) {
        return mapper.selectBillingDetails(billingId, detail);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createRepayment(Repayment repayment) {
        mapper.insertRepayment(repayment);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteRepayment(int repaymentId) {
        mapper.deleteRepayment(repaymentId);
    }

    @Override
    public Repayment getRepayment(int repaymentId) {
        return mapper.selectRepayment(repaymentId);
    }

    @Override
    public List<Repayment> getRepayments(int billingDetailId) {
        return mapper.selectRepayments(billingDetailId);
    }

}
