package com.jbb.mgt.core.service;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.UserAddresses;
import com.jbb.server.common.Home;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class UserAddressServiceTest {

    @Autowired
    private UserAddressService service;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testUserAddressService() {
        UserAddresses ua = new UserAddresses();

        service.deleteUserAddressesByAddressId(1);
        UserAddresses u1 = service.getUserAddressesByAddressId(1);
        Assert.assertTrue(null == u1);
        ua.setAddressId(1);
        ua.setIsDeleted(0);
        ua.setAddress("1");
        service.updateUserAddresses(ua);

        /*ua.setAddress("1");
        service.saveUserAddresses(ua);
        UserAddresses u2 = service.getUserAddressesByAddressId(16);
        Assert.assertTrue(u2.getAddress().equals("1"));*/

        /*ua.setAddressId(1);
        ua.setAddress("2");
        service.updateUserAddresses(ua);
        UserAddresses u3 = service.getUserAddressesByAddressId(1);
        Assert.assertTrue(u3.getAddress().equals("2"));


        service.deleteUserAddressesByAddressId(1);
        UserAddresses list = service.getUserAddressesByAddressId(1);
        Assert.assertTrue(null == list);*/
    }

}
