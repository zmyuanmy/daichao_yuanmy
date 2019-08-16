package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.OrgAssignSetting;

import java.util.List;

/**
 *
 *
 * @author wyq
 * @date 2018/7/12 15:37
 */
public interface OrgAssignSettingService {
    List<OrgAssignSetting> selectOrgAssignSetting(Integer orgId, Integer assignType);

    void saveOrgAssignSetting(OrgAssignSetting orgAssignSetting);
}
