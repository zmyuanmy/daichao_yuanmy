package com.jbb.mgt.core.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.OrgChannelStatisticDaily;
import com.jbb.server.common.Home;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class OrgChannelStatisticDailyServiceTest {

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Autowired
    private OrgChannelStatisticDailyService service;
    
    @Test
    public void testOrgChannelStatisticDaily() {
        List<OrgChannelStatisticDaily> list = service.selectFinOrgStatisticDaily(1, 22, "2017-07-25", "2017-07-30");
        assertNotNull(list);
    }
}
