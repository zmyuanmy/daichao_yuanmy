package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.OrgDFlowBaseDao;
import com.jbb.mgt.core.dao.mapper.OrgDFlowBaseMapper;
import com.jbb.mgt.core.domain.DataFlowBase;
import com.jbb.mgt.core.domain.OrgDflowBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 组织进件导流特殊配置Dao接口实现类
 *
 * @author wyq
 * @date 2018/7/30 10:21
 */
@Repository("OrgDFlowBaseDao")
public class OrgDFlowBaseDaoImpl implements OrgDFlowBaseDao {

    @Autowired
    private OrgDFlowBaseMapper mapper;

    @Override
    public void insertOrgDFlowBase(List<OrgDflowBase> list) {
        mapper.insertOrgDFlowBase(list);
    }

    @Override
    public List<DataFlowBase> selectOrgDflowBase(int orgId, Boolean includeHiddenF) {
        return mapper.selectOrgDflowBase(orgId, includeHiddenF);
    }

    @Override
    public void deleteOrgDflowBase(int orgId) {
        mapper.deleteOrgDflowBase(orgId);
    }

    @Override
    public int[] selectdflowId(Integer orgId) {
        return mapper.selectdflowId(orgId);
    }

}
