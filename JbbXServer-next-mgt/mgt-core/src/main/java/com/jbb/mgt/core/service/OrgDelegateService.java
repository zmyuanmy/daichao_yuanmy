 package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.OrganizationDelegateInfo;

public interface OrgDelegateService {
     
     OrganizationDelegateInfo recommandDelegateChannelCode();
     
     
     OrganizationDelegateInfo recommandDelegateChannelCode(Integer orgId);
}
