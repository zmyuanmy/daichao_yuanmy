package com.jbb.mgt.core.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.XjlPayBankDao;
import com.jbb.mgt.core.dao.mapper.XjlPayBankMapper;
import com.jbb.mgt.core.domain.XjlPayBank;

@Repository("XjlPayBankDao")
public class XjlPayBankDaoImpl implements XjlPayBankDao {

    @Autowired
    private XjlPayBankMapper mapper;

    @Override
    public List<XjlPayBank> selectXjlPayBank(String payProductId) {
        return mapper.selectXjlPayBank(payProductId);
    }

}
