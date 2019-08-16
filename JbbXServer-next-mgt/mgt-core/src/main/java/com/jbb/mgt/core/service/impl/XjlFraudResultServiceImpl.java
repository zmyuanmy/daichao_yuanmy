package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.XjlAntFraudResultDao;
import com.jbb.mgt.core.service.XjlAntFraudResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("XjlAntFraudResultService")
public class XjlFraudResultServiceImpl implements XjlAntFraudResultService {

    @Autowired
    private XjlAntFraudResultDao xjlAntFraudResultDao;

    @Override
    public void editXjlFraudResult(int orderId, String antiFraudResult) {
        xjlAntFraudResultDao.updateXjlFraudResult(orderId,antiFraudResult);
    }
}
