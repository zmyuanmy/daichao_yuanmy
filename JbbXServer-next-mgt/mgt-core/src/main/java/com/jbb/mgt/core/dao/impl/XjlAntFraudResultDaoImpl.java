package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.XjlAntFraudResultDao;
import com.jbb.mgt.core.dao.mapper.XjlAntFraudResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("XjlAntFraudResultDao")
public class XjlAntFraudResultDaoImpl implements XjlAntFraudResultDao{

    @Autowired
    private XjlAntFraudResultMapper mapper;

    @Override
    public void updateXjlFraudResult(int orderId, String antiFraudResult) {
         mapper.update(orderId,antiFraudResult);
    }
}
