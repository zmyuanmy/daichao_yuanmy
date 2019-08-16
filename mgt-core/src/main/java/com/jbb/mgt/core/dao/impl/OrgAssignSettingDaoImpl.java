package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.OrgAssignSettingDao;
import com.jbb.mgt.core.dao.mapper.OrgAssignSettingMapper;
import com.jbb.mgt.core.domain.OrgAssignSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @author wyq
 * @date 2018/7/12 15:29
 */
@Repository("OrgAssignSettingDao")
public class OrgAssignSettingDaoImpl implements OrgAssignSettingDao {
    @Autowired
    private OrgAssignSettingMapper mapper;

    @Override
    public List<OrgAssignSetting> selectOrgAssignSetting(Integer orgId, Integer assignType) {
        return mapper.selectOrgAssignSetting(orgId, assignType);
    }

    @Override
    public void saveOrgAssignSetting(OrgAssignSetting orgAssignSetting) {
        mapper.saveOrgAssignSetting(orgAssignSetting);
    }
}
