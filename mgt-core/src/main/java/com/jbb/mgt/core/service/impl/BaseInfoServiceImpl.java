package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.BaseInfoDao;
import com.jbb.mgt.core.domain.*;
import com.jbb.mgt.core.service.BaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("BaseInfoService")
public class BaseInfoServiceImpl implements BaseInfoService {

    @Autowired
    private BaseInfoDao baseInfoDao;

    @Override
    public List<EducationBase> selectEducationBases() {
        return baseInfoDao.selectEducationBases();
    }

    @Override
    public List<MaritalStatusBase> selectMaritalStatusBases() {
        return baseInfoDao.selectMaritalStatusBases();
    }

    @Override
    public List<LoanPurposeBase> selectLoanPurposeBases() {
        return baseInfoDao.selectLoanPurposeBases();
    }

    @Override
    public List<RelationBase> selectRelationBases() {
        return baseInfoDao.selectRelationBases();
    }

    @Override
    public List<ProfessionTypeBase> selectProfessionTypeBases() {
        return baseInfoDao.selectProfessionTypeBases();
    }
}
