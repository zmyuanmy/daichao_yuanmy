package com.jbb.server.core.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.server.common.Home;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.core.dao.AccountDao;
import com.jbb.server.core.dao.mapper.AccountMapper;
import com.jbb.server.core.util.LenderesUtil;
import com.jbb.server.core.util.PropertiesUtil;
import com.jbb.server.shared.rs.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class UserRegisterProcessServiceTest {
    @Autowired
    private UserRegisterProcessService userRegisterProcessService2;

    @Autowired
    private AccountDao accountDao;

    @BeforeClass
    public static void oneTimeSetUp() {
        PropertiesUtil.init();
        LenderesUtil.init();
        Home.getHomeDir();
        Home.settingsTest();
    }

//    @Test
    public void testApplyToUser() throws InterruptedException {
        for (int i = 1245243; i <= 1246522; i++) {
          
            accountDao.saveUserApplyRecord(i, 1243508);
            System.out.println("=======" + i + "   to 1243508 at " + Util.printDateTime(DateUtil.getCurrentTimeStamp()));
            int random = (int)(Math.random() * 30);
            Thread.sleep(random * 1000);
            
        }
    }

    // @Test
    public void test() throws InterruptedException {

        // Thread.sleep(60000);

        //
        // for (int i = 0; i < 100; i++) {
        // userRegisterProcessService2.applyToLendUser(1000000);
        // Thread.sleep(2000);
        // }
        //
        userRegisterProcessService2.printCnt();
        //
        // userRegisterProcessService2.applyToLendUser(1044440);

        // userRegisterProcessService2.applyToLendUser(1052555);
        // userRegisterProcessService2.applyToLendUser(1052556);

        for (int i = 1245242; i <= 1246522; i++) {
            int random = (int)(Math.random() * 10);
            System.out.println(i);
            userRegisterProcessService2.applyToLendUser(i);
            Thread.sleep(random * 30);
        }

        userRegisterProcessService2.printCnt();
        // userRegisterProcessService2.applyToLendUser(1000001);
        // userRegisterProcessService2.printCnt();
        //
        // userRegisterProcessService2.applyToLendUser(1000002);
        // userRegisterProcessService2.printCnt();
    }
}
