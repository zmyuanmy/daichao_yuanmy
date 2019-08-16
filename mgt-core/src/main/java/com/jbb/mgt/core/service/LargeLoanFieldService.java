package com.jbb.mgt.core.service;

import java.util.List;

import com.jbb.mgt.core.domain.LargeLoanField;

public interface LargeLoanFieldService {

    List<LargeLoanField> getLoanFields(boolean required, boolean includeValue);

    List<String> getRequiredFields();

}
