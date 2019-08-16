package com.jbb.mgt.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.AgreementDao;
import com.jbb.mgt.core.domain.Agreement;
import com.jbb.mgt.core.service.AgreementService;

@Service("AgreementService")
public class AgreementServiceImpl implements AgreementService {

    @Autowired
    private AgreementDao agreementDao;

    @Override
    public List<Agreement> getAgreement(int orgId) {
        return agreementDao.selectAgreement(orgId);
    }

}
