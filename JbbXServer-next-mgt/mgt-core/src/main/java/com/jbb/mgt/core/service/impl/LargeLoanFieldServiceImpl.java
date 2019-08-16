package com.jbb.mgt.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.LargeLoanFieldDao;
import com.jbb.mgt.core.domain.LargeLoanField;
import com.jbb.mgt.core.service.LargeLoanFieldService;

@Service("LargeLoanFieldService")
public class LargeLoanFieldServiceImpl implements LargeLoanFieldService {
    @Autowired
    private LargeLoanFieldDao largeLoanFieldDao;

    @Override
    public List<LargeLoanField> getLoanFields(boolean required, boolean includeValue) {
        return largeLoanFieldDao.getLoanFields(required, includeValue);
    }

    @Override
    public List<String> getRequiredFields() {
        return largeLoanFieldDao.getRequiredFields();
    }

}
