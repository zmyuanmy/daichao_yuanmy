package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.OrgDFlowBaseDao;
import com.jbb.mgt.core.domain.DataFlowBase;
import com.jbb.mgt.core.domain.OrgDflowBase;
import com.jbb.mgt.core.service.OrgDFlowBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组织进件导流特殊配置Service接口实现类
 *
 * @author wyq
 * @date 2018/7/30 10:10
 */
@Service("OrgDFlowBaseService")
public class OrgDFlowBaseServiceImpl implements OrgDFlowBaseService {

    @Autowired
    private OrgDFlowBaseDao dao;

    @Override
    public void saveOrgDFlowBase(List<OrgDflowBase> list) {
        dao.insertOrgDFlowBase(list);
    }

    @Override
    public List<DataFlowBase> getOrgDflowBase(int orgId, Boolean includeHiddenF) {
        return dao.selectOrgDflowBase(orgId, includeHiddenF);
    }

    @Override
    public void deleteOrgDflowBase(int orgId) {
        dao.deleteOrgDflowBase(orgId);
    }

    @Override
    public int[] gerdflowId(Integer orgId) {
        return dao.selectdflowId(orgId);
    }
}
