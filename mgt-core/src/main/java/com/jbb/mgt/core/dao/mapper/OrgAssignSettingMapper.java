package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.OrgAssignSetting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 *
 * @author wyq
 * @date 2018/7/12 15:32
 */
public interface OrgAssignSettingMapper {
    List<OrgAssignSetting> selectOrgAssignSetting(@Param(value = "orgId") Integer orgId,
        @Param(value = "assignType") Integer assignType);

    void saveOrgAssignSetting(OrgAssignSetting orgAssignSetting);
}
