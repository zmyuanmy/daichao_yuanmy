package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.FinOrgSalesRelationDao;
import com.jbb.mgt.core.domain.FinOrgSalesRelation;
import com.jbb.mgt.core.service.FinOrgSalesRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("FinOrgSalesRelationService")
public class FinOrgSalesRelationServiceImpl implements FinOrgSalesRelationService {

    @Autowired
    private FinOrgSalesRelationDao finOrgSalesRelationDao;

    @Override
    public void insertFinOrgSalesRelation(FinOrgSalesRelation finOrgSalesRelation) {
        finOrgSalesRelationDao.insertFinOrgSalesRelation(finOrgSalesRelation);
    }

    @Override
    public boolean checkExist(int orgId, int accountId) {
        return finOrgSalesRelationDao.checkExist(orgId, accountId);
    }

    @Override
    public boolean updateFinOrgSalesRelation(int orgId, int accountId,boolean deleted) {
        return finOrgSalesRelationDao.updateFinOrgSalesRelation(orgId, accountId,deleted);
    }

    @Override
    public FinOrgSalesRelation selectOrgSalesRelationByOrgId(int orgId) {
        return finOrgSalesRelationDao.selectOrgSalesRelationByOrgId(orgId);
    }

    @Override
    public boolean deleteFinOrgSalesRelation(int orgId, int accountId) {
        return finOrgSalesRelationDao.deleteFinOrgSalesRelation(orgId, accountId);
    }
}
