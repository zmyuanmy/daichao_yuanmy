package com.jbb.mgt.core.dao;

import java.util.List;

import com.jbb.mgt.core.domain.LargeLoanField;

public interface LargeLoanFieldDao {

    List<LargeLoanField> getLoanFields(boolean required, boolean includeValue);

    List<String> getRequiredFields();

}
