package com.jbb.mgt.core.service;

import java.sql.Timestamp;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.dao.UserDao;
import com.jbb.mgt.core.domain.mapper.Mapper;
import com.jbb.mgt.core.domain.mq.RegisterEvent;
import com.jbb.mgt.server.core.util.OrgDataFlowSettingsUtil;
import com.jbb.mgt.server.core.util.PropertiesUtil;
import com.jbb.mgt.server.core.util.RegisterEventUtil;
import com.jbb.server.common.Home;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.mq.MqClient;
import com.jbb.server.mq.Queues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class RegisterEventServiceTest {

    @Autowired
    private RegisterEventService registerEventService;

    @Autowired
    private UserDao userDao;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
        PropertiesUtil.init();
        RegisterEventUtil.init();
        OrgDataFlowSettingsUtil.init();
    }

    @AfterClass
    public static void after() {
        OrgDataFlowSettingsUtil.destroy();
        PropertiesUtil.destroy();
        RegisterEventUtil.destroy();
    }

    // @Test
    public void testProcess() throws InterruptedException {

        // for (int i = 0; i < 1; i++) {
        // RegisterEvent event = new RegisterEvent(2, 56, "jbbd");
        // registerEventService.processEventToSelfOrgs(event);
        // Thread.sleep(5000);
        // }

        RegisterEvent event = new RegisterEvent(1, 10426, "lxud7M", 1);
        registerEventService.processMessage(event);

        Thread.sleep(3000000);

        //
        // RegisterEvent event2 = new RegisterEvent(2, 56, "jbbd");
        // registerEventService.processMessage(event2);

    }

    // @Test
    public void sendRegisterEventToMq() {

        RegisterEvent event = new RegisterEvent(2, 10010044, "jbbd", 1);
        MqClient.send(Queues.JBB_MGT_USER_REGISTER_QUEUE_ADDR, Mapper.toBytes(event), 300000);

    }

    private boolean isAppliedInXDays(int userId, int days) {
        long todayStart = DateUtil.getStartTsOfDay(DateUtil.getCurrentTime());
        Timestamp start = new Timestamp(todayStart - days * DateUtil.DAY_MILLSECONDES);
        return userDao.checkUserApplied(userId, start);
    }

    // @Test
    public void testProcess2() throws InterruptedException {

        // for (int i = 0; i < 1; i++) {
        // RegisterEvent event = new RegisterEvent(2, 56, "jbbd");
        // registerEventService.processEventToSelfOrgs(event);
        // Thread.sleep(5000);
        // }

        // RegisterEvent event = new RegisterEvent(1, 10426, "lxud7M");
        // registerEventService.processMessage(event);
        //
        //
        Thread.sleep(3000000);

        //
        // RegisterEvent event2 = new RegisterEvent(2, 56, "jbbd");
        // registerEventService.processMessage(event2);

    }

}
