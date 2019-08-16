package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.UserDevice;
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
public class UserDeviceServiceTest {

    @Autowired
    private UserDeviceService service;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testUserDeviceService() {
        UserDevice userDevice = new UserDevice();
        userDevice.setUserId(1);
        userDevice.setUuid("1");
        userDevice.setManufacturer("1");
        service.saveUserDevice(userDevice);

        List<UserDevice> list = service.getUserDevices(1);
        Assert.assertTrue(list.get(0).getManufacturer().equals("1"));

        UserDevice ud = service.getUserLastDevice(1);
        Assert.assertTrue(ud.getManufacturer().equals("2"));

    }
}
