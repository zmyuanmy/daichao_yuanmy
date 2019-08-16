package com.jbb.mgt.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.LargeLoanField;

public interface LargeLoanFieldMapper {

    List<LargeLoanField> getLoanFields(@Param("required") boolean required,
        @Param("includeValue") boolean includeValue);

    List<String> getRequiredFields();

}
