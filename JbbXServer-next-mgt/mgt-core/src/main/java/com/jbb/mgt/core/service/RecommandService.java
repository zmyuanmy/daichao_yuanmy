package com.jbb.mgt.core.service;

import java.util.List;
import java.util.Set;

import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.domain.User;

public interface RecommandService {

    /**
     * 获取推荐的出借组织
     * 
     * @param user
     * @return
     */

    List<Organization> getRecommandOrgs(User user, Channel channel, int userType, boolean entryStatus);

    /**
     * 提交至出借组织
     * 
     * @param user
     * @param orgIds
     */
    void applyToOrgs(User user, List<Organization> orgs);
    
    void applyToOrg(User user, int orgId, int userType, String channelCode);

    Set<Integer> filterOrgsByGroupPolicy(Set<Integer> toChooseOrgs, String xorGroupsPolicyStr);

    void applyToOrg(User user, int orgId, int userType, String channelCode, int flag);
}
