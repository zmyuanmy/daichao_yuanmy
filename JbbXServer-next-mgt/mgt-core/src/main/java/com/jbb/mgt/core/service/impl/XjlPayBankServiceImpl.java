package com.jbb.mgt.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.XjlPayBankDao;
import com.jbb.mgt.core.domain.XjlPayBank;
import com.jbb.mgt.core.service.XjlPayBankService;

@Service("XjlPayBankService")
public class XjlPayBankServiceImpl implements XjlPayBankService {

    @Autowired
    private XjlPayBankDao xjlPayBankDao;

    @Override
    public List<XjlPayBank> selectXjlPayBank(String payProductId) {
        return xjlPayBankDao.selectXjlPayBank(payProductId);
    }

}
