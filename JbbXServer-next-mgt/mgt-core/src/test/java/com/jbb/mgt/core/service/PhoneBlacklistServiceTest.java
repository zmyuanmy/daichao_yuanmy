package com.jbb.mgt.core.service;

import com.jbb.server.common.Home;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by inspironsun on 2019/4/11
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class PhoneBlacklistServiceTest {

    @Autowired
    private PhoneBlacklistService phoneBlacklistService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testBlacklist(){
        String phoneNumber = "13333333333";
        phoneBlacklistService.checkPhoneNumberExist(phoneNumber);
    }
}
