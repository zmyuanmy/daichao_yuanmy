 package com.jbb.mgt.core.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.OrganizationDelegateInfo;
import com.jbb.mgt.server.core.util.OrgDataFlowSettingsUtil;
import com.jbb.mgt.server.core.util.PropertiesUtil;
import com.jbb.server.common.Home;

@RunWith(SpringJUnit4ClassRunner.class)
 @ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})

 public class OrgDelegateServiceTest {
    
    
    @Autowired
    private OrgDelegateService orgDelegateServiceTest;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
        Home.settingsTest();
        
        PropertiesUtil.init();
        OrgDataFlowSettingsUtil.init();
    }

    @Test
    public void testGetRecommandCode() {
        OrganizationDelegateInfo info = orgDelegateServiceTest.recommandDelegateChannelCode();
        
        System.out.println("========" + info);
    }

}
