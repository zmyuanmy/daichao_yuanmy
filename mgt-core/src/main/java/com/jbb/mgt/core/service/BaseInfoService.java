package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.*;

import java.util.List;

public interface BaseInfoService {

    List<EducationBase> selectEducationBases();

    List<MaritalStatusBase> selectMaritalStatusBases();

    List<LoanPurposeBase> selectLoanPurposeBases();

    List<RelationBase> selectRelationBases();

    List<ProfessionTypeBase> selectProfessionTypeBases();
}
