package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.UserLcDevice;
import com.jbb.server.common.Home;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})

public class UserLcDeviceServiceTest {

    @Autowired
    private UserLcDeviceService service;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testUserLcDeviceService() {
//        List<UserLcDevice> list = service.getUserLcDeviceListByUserId(1, "1");
//        Assert.assertTrue(list.size() > 0);
        UserLcDevice ud = new UserLcDevice();
        ud.setObjectId("3");
        ud.setUserId(1);
        ud.setDeviceType("3");
        ud.setAppName("1");
//        service.saveUserDevice(ud);
//        UserLcDevice ud2 = service.selectUserLcDeviceListByObjectId("1");
        List<UserLcDevice> list = service.getUserLcDeviceListByUserId(1, "1");
    }

}
