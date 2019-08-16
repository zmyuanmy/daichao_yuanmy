package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.FinFile;
import com.jbb.mgt.core.domain.FinOrgSalesRelation;
import org.apache.ibatis.annotations.Param;

public interface FinOrgSalesRelationMapper {

    int insertFinOrgSalesRelation(FinOrgSalesRelation finOrgSalesRelation);

    int checkExist(@Param("orgId") int orgId,@Param("accountId") int accountId);

    boolean updateFinOrgSalesRelation(@Param("orgId") int orgId,@Param("accountId") int accountId,@Param("deleted") boolean deleted);

    FinOrgSalesRelation selectFinOrgSalesRelationByOrgId(@Param("orgId") int orgId);

    boolean deleteFinOrgSalesRelation(@Param("orgId") int orgId,@Param("accountId") int accountId);
}
