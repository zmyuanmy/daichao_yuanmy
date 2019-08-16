package com.jbb.mgt.core.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.LargeLoanFieldDao;
import com.jbb.mgt.core.dao.mapper.LargeLoanFieldMapper;
import com.jbb.mgt.core.domain.LargeLoanField;

@Repository("LargeLoanFieldDao")
public class LargeLoanFieldDaoImpl implements LargeLoanFieldDao {
    @Autowired
    private LargeLoanFieldMapper mapper;

    @Override
    public List<LargeLoanField> getLoanFields(boolean required, boolean includeValue) {

        return mapper.getLoanFields(required, includeValue);
    }

    @Override
    public List<String> getRequiredFields() {
        // TODO Auto-generated method stub
        return mapper.getRequiredFields();
    }

}
