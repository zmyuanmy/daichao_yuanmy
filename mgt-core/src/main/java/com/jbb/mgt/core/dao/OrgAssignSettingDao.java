package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.OrgAssignSetting;

import java.util.List;

/**
 *
 *
 * @author wyq
 * @date 2018/7/12 15:15
 */
public interface OrgAssignSettingDao {
    List<OrgAssignSetting> selectOrgAssignSetting(Integer orgId, Integer assignType);

    void saveOrgAssignSetting(OrgAssignSetting orgAssignSetting);
}
