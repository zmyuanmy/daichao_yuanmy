package com.jbb.mgt.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.DataFlowBaseDao;
import com.jbb.mgt.core.domain.DataFlowBase;
import com.jbb.mgt.core.service.DataFlowBaseService;

/**
 * 流量基本信息service实现类
 * 
 * @author wyq
 * @date 2018/04/27
 */
@Service("DataFlowBaseService")
public class DataFlowBaseServiceImpl implements DataFlowBaseService {

    @Autowired
    private DataFlowBaseDao dao;

    @Override
    public List<DataFlowBase> getDataFlowBases(boolean includeDeleted) {
         return dao.selectDataFlowBase(includeDeleted);
    }

   

}
