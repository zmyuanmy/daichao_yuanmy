package com.jbb.mgt.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.JiGuangDao;
import com.jbb.mgt.core.dao.mapper.JiGuangMapper;
import com.jbb.mgt.core.domain.JiGuangUserDevice;

@Repository("JiGuangDao")
public class JiGuangDaoImpl implements JiGuangDao {
    @Autowired
    private JiGuangMapper mapper;

    @Override
    public void saveUserDevice(JiGuangUserDevice userDevice) {
        mapper.saveUserDevice(userDevice);
    }

}
