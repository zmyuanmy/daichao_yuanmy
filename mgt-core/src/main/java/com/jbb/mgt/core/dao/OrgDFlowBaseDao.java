package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.DataFlowBase;
import com.jbb.mgt.core.domain.OrgDflowBase;

import java.util.List;

/**
 * 组织进件导流特殊配置Dao接口
 *
 * @author wyq
 * @date 2018/7/30 10:18
 */
public interface OrgDFlowBaseDao {

    void insertOrgDFlowBase(List<OrgDflowBase> list);

    List<DataFlowBase> selectOrgDflowBase(int orgID, Boolean includeHiddenF);

    void deleteOrgDflowBase(int orgId);

    int[] selectdflowId(Integer orgId);
}
