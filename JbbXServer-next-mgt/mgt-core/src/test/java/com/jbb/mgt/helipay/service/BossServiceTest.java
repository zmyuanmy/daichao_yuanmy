package com.jbb.mgt.helipay.service;

import com.jbb.boss.service.BossService;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.service.UserService;
import com.jbb.server.common.Home;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class BossServiceTest {

    private static int USERID = 10000;

    @Autowired
    private BossService bossService;

    @Autowired
    private UserService userService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testBackFlowAddressBook() throws Exception {
        User user = userService.selectUserById(USERID);
        bossService.backFlowAddressBook(user);
    }

    @Test
    public void testFlowMessage() throws Exception {
        User user = userService.selectUserById(USERID);
        bossService.backFlowMessage(user);
    }

    @Test
    public void testFlowAppInfo() throws Exception {
        User user = userService.selectUserById(USERID);
        bossService.backFlowAppInfo(user);
    }

    @Test
    public void testFlowOperatorInfo() throws Exception {
        User user = userService.selectUserById(USERID);
        bossService.backFlowOperatorInfo(user);
    }

    @Test
    public void testBackFlowMessage() throws Exception {
        User user = userService.selectUserById(USERID);
        bossService.backFlowMessage(user);
    }

    @Test
    public void testGetBossReportRequest() throws Exception {
        User user = userService.selectUserById(USERID);
        bossService.getBossReportRequest(user);
    }

}
