package com.jbb.mgt.core.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.OrgAssignSetting;
import com.jbb.server.common.Home;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})

public class OrgAssignSettingServiceTest {

    @Autowired
    private OrgAssignSettingService service;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testorgService() {
//        List<OrgAssignSettings> list = service.selectOrgAssignSetting(1, null);
//        assertTrue(list.size() >= 0);
        service.saveOrgAssignSetting(new OrgAssignSetting(2,3,true,"1"));
    }

}
