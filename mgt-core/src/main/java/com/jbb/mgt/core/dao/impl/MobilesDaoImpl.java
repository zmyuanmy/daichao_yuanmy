package com.jbb.mgt.core.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.MobilesDao;
import com.jbb.mgt.core.dao.mapper.MobilesMapper;
import com.jbb.mgt.core.domain.Mobiles;

@Repository("MobilesDao")
public class MobilesDaoImpl implements MobilesDao {

    @Autowired
    private MobilesMapper mapper;

    @Override
    public void insertMobiles(Mobiles mobiles) {
        mapper.insertMobiles(mobiles);
    }

    @Override
    public List<Mobiles> selectMobiles(String phoneNumber, String checkType) {
        return mapper.selectMobiles(phoneNumber, checkType);
    }

}
