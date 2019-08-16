package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.*;

import java.util.List;

public interface BaseInfoDao {

    List<EducationBase> selectEducationBases();

    List<MaritalStatusBase> selectMaritalStatusBases();

    List<LoanPurposeBase> selectLoanPurposeBases();

    List<RelationBase> selectRelationBases();

    List<ProfessionTypeBase> selectProfessionTypeBases();
}
