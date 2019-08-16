
package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.FinOrgSalesRelation;

public interface FinOrgSalesRelationService {
    void insertFinOrgSalesRelation(FinOrgSalesRelation finOrgRelSalesRelation);

    boolean checkExist(int orgId, int accountId);

    boolean updateFinOrgSalesRelation(int orgId, int accountId,boolean deleted);

    FinOrgSalesRelation selectOrgSalesRelationByOrgId(int orgId);

    boolean deleteFinOrgSalesRelation(int orgId, int accountId);
}
