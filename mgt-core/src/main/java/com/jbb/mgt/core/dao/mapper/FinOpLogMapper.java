package com.jbb.mgt.core.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.jbb.mgt.core.domain.FinOpLog;

@Mapper
public interface FinOpLogMapper {

    void insertFinOpLog(FinOpLog finOpLog);

}
