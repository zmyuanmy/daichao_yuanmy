package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.DataFlowBase;
import com.jbb.mgt.core.domain.OrgDflowBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 组织进件导流特殊配置Mapper接口
 *
 * @author wyq
 * @date 2018/7/30 10:23
 */
public interface OrgDFlowBaseMapper {

    void insertOrgDFlowBase(List<OrgDflowBase> list);

    List<DataFlowBase> selectOrgDflowBase(@Param(value = "orgId") int orgId,
        @Param(value = "includeDeleted") Boolean includeHiddenF);

    void deleteOrgDflowBase(@Param(value = "orgId") int orgId);

    int[] selectdflowId(@Param(value = "orgId") Integer orgId);
}
