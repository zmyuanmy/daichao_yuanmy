package com.jbb.mgt.core.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.OrgAssignSetting;
import com.jbb.mgt.core.domain.User;
import com.jbb.server.common.Home;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.shared.rs.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})

public class OrgAppChannelUserServiceTest {

    @Autowired
    private OrgAppChannelUserService service;

    @Autowired
    private UserService userService;

    @Autowired
    private ChannelService channelService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testSaveOrgAppChannelUser() {

        String channelCode = "jbbd";
        Channel channel = channelService.getChannelByCode(channelCode);
        int orgId = channel.getAccount().getOrgId();

        int userType = 2;

        Timestamp start = Util.parseTimestamp("2018-09-19T15:00:00", TimeZone.getDefault());
        Timestamp end = Util.parseTimestamp("2018-09-19T20:00:00", TimeZone.getDefault());

        List<User> users =
            userService.selectUserApplyDetails(channelCode, start, end, null, userType, true, true, true);
        if (CommonUtil.isNullOrEmpty(users)) {
            return;
        }

        boolean isNew = false;
        boolean hidden = false;
        int applicationId = 2;
        for (User user : users) {
            service.saveOrgAppChannelUser(user, orgId, isNew, hidden, channel, applicationId, userType, "127.0.0.0");
        }

    }
}
