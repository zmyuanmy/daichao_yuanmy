package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.OrgAssignSettingDao;
import com.jbb.mgt.core.domain.OrgAssignSetting;
import com.jbb.mgt.core.service.OrgAssignSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * 
 * @author wyq
 * @date 2018/7/12 15:38
 */
@Service("OrgAssignSettingService")
public class OrgAssignSettingServiceImpl implements OrgAssignSettingService {
    @Autowired
    private OrgAssignSettingDao dao;

    @Override
    public List<OrgAssignSetting> selectOrgAssignSetting(Integer orgId, Integer assignType) {
        return dao.selectOrgAssignSetting(orgId, assignType);
    }

    @Override
    public void saveOrgAssignSetting(OrgAssignSetting orgAssignSetting) {
        dao.saveOrgAssignSetting(orgAssignSetting);
    }
}
