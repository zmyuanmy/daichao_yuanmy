package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.BaseInfoDao;
import com.jbb.mgt.core.dao.mapper.BaseInfoMapper;
import com.jbb.mgt.core.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("BaseInfoDao")
public class BaseInfoDaoImpl implements BaseInfoDao {

    @Autowired
    private BaseInfoMapper baseInfoMapper;

    @Override
    public List<EducationBase> selectEducationBases() {
        return baseInfoMapper.selectEducationBases();
    }

    @Override
    public List<MaritalStatusBase> selectMaritalStatusBases() {
        return baseInfoMapper.selectMaritalStatusBases();
    }

    @Override
    public List<LoanPurposeBase> selectLoanPurposeBases() {
        return baseInfoMapper.selectLoanPurposeBases();
    }

    @Override
    public List<RelationBase> selectRelationBases() {
        return baseInfoMapper.selectRelationBases();
    }

    @Override
    public List<ProfessionTypeBase> selectProfessionTypeBases() {
        return baseInfoMapper.selectProfessionTypeBases();
    }
}
