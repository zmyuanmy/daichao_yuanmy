package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.FinOrgSalesRelation;
import org.apache.ibatis.annotations.Param;

public interface FinOrgSalesRelationDao {

    void insertFinOrgSalesRelation(FinOrgSalesRelation finOrgSalesRelation);

    boolean checkExist(int orgId,int accountId);

    boolean updateFinOrgSalesRelation(int orgId, int accountId,boolean deleted);

    FinOrgSalesRelation selectOrgSalesRelationByOrgId(int orgId);

    boolean deleteFinOrgSalesRelation(int orgId, int accountId);
}
