package com.jbb.mgt.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.MobileWoolCheckDao;
import com.jbb.mgt.core.dao.mapper.MobileWoolCheckMapper;
import com.jbb.mgt.core.domain.ChuangLanWoolCheck;
@Repository("MobileWoolCheckDao")
public class MobileWoolCheckDaoImpl implements MobileWoolCheckDao {
    @Autowired
    private MobileWoolCheckMapper mapper;

    @Override
    public void insertWoolCheckResult(ChuangLanWoolCheck data) {
        mapper.insertWoolCheckResult(data);
    }

    @Override
    public ChuangLanWoolCheck getWoolCheckResult(String mobile) {
        return mapper.getWoolCheckResult(mobile);
    }

}
