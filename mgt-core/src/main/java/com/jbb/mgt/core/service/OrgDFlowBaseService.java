package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.DataFlowBase;
import com.jbb.mgt.core.domain.OrgDflowBase;

import java.util.List;

/**
 * 组织进件导流特殊配置Service接口
 *
 * @author wyq
 * @date 2018/7/30 09:51
 */
public interface OrgDFlowBaseService {

    void saveOrgDFlowBase(List<OrgDflowBase> list);

    List<DataFlowBase> getOrgDflowBase(int orgId, Boolean includeHiddenF);

    void deleteOrgDflowBase(int orgId);

    int[] gerdflowId(Integer orgId);
}
