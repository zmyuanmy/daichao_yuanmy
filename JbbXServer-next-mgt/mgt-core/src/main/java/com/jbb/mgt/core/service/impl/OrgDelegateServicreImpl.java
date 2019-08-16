package com.jbb.mgt.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.ChannelDao;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.domain.OrganizationDelegateInfo;
import com.jbb.mgt.core.service.OrgDelegateService;
import com.jbb.mgt.server.core.util.OrgDataFlowSettingsUtil;
import com.jbb.server.common.util.CommonUtil;

@Service("OrgDelegateService")
public class OrgDelegateServicreImpl implements OrgDelegateService {
    @Autowired
    private ChannelDao channelDao;

    @Override
    public OrganizationDelegateInfo recommandDelegateChannelCode() {
        Integer totalWeight = 0;
        List<Organization> orgs = new ArrayList<Organization>();

        for (Organization org : OrgDataFlowSettingsUtil.ORGS.values()) {
            if (org.isDelegateEnabled()) {
                totalWeight += org.getDelegateWeight();
                orgs.add(org);
            }
        }

        if (CommonUtil.isNullOrEmpty(orgs)) {
            return null;
        }

        int p = 0;
        double random = Math.random() * totalWeight;

        Organization choosedOrg = null;
        for (Organization org : orgs) {
            p += org.getDelegateWeight();
            if (p >= random) {

                choosedOrg = org;
                break;
            }
        }
        if (choosedOrg == null) {
            return null;
        }

        OrganizationDelegateInfo info = new OrganizationDelegateInfo();

        info.setOrg(choosedOrg);

        Channel channel = channelDao.selectOrgDelegateChannel(choosedOrg.getOrgId());
        info.setChannel(channel);

        return info;
    }

    @Override
    public OrganizationDelegateInfo recommandDelegateChannelCode(Integer orgId) {
        if (orgId == null) {
            return recommandDelegateChannelCode();
        } else {
            OrganizationDelegateInfo info = new OrganizationDelegateInfo();
            Organization choosedOrg = OrgDataFlowSettingsUtil.ORGS.get(orgId);
            if (choosedOrg == null) {
                return null;
            }
            info.setOrg(choosedOrg);
            Channel channel = channelDao.selectOrgDelegateChannel(orgId);
            info.setChannel(channel);
            return info;
        }

    }

}
