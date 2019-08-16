package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.ChannelAccountInfo;
import com.jbb.server.common.Home;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class ChannelAccountInfoServiceTest {

    @Autowired
    private ChannelAccountInfoService service;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testChannelService() {
        ChannelAccountInfo cai = new ChannelAccountInfo();
        cai.setChannelCode("jbba");
//        service.insertChannelAccountInfo(cai);
        ChannelAccountInfo jbbd = service.selectChannelAccountInfo("jbbd");
        assertEquals("jbbd",jbbd.getChannelCode());
    }
}