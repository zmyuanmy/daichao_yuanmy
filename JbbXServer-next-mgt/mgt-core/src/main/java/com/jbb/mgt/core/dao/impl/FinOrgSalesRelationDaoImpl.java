package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.FinOrgSalesRelationDao;
import com.jbb.mgt.core.dao.mapper.FinOrgSalesRelationMapper;
import com.jbb.mgt.core.domain.FinOrgSalesRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("FinOrgSalesRelationDao")
public class FinOrgSalesRelationDaoImpl implements FinOrgSalesRelationDao {

    @Autowired
    private FinOrgSalesRelationMapper finOrgSalesRelationMapper;

    @Override
    public void insertFinOrgSalesRelation(FinOrgSalesRelation finOrgSalesRelation) {
        finOrgSalesRelationMapper.insertFinOrgSalesRelation(finOrgSalesRelation);
    }

    @Override
    public boolean checkExist(int orgId, int accountId) {
        return finOrgSalesRelationMapper.checkExist(orgId, accountId) > 0;
    }

    @Override
    public boolean updateFinOrgSalesRelation(int orgId, int accountId, boolean deleted) {
        return finOrgSalesRelationMapper.updateFinOrgSalesRelation(orgId, accountId, deleted);
    }

    @Override
    public FinOrgSalesRelation selectOrgSalesRelationByOrgId(int orgId) {
        return finOrgSalesRelationMapper.selectFinOrgSalesRelationByOrgId(orgId);
    }

    @Override
    public boolean deleteFinOrgSalesRelation(int orgId, int accountId) {
        return finOrgSalesRelationMapper.deleteFinOrgSalesRelation(orgId, accountId);
    }
}
