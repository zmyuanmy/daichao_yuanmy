package com.jbb.mgt.core.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.AgreementDao;
import com.jbb.mgt.core.dao.mapper.AgreementMapper;
import com.jbb.mgt.core.domain.Agreement;

@Repository("AgreementDao")
public class AgreementDaoImpl implements AgreementDao {

    @Autowired
    private AgreementMapper mapper;

    @Override
    public List<Agreement> selectAgreement(int orgId) {
        return mapper.selectAgreement(orgId);
    }

}
