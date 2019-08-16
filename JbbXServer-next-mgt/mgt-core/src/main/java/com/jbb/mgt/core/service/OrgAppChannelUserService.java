package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.User;

public interface OrgAppChannelUserService {

    void saveOrgAppChannelUser(User user, int orgId, boolean isNew, boolean hidden, Channel channel,
        int applicationId, Integer userType, String remoteAddress);

}
