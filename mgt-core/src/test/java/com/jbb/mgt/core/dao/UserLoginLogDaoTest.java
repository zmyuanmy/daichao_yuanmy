package com.jbb.mgt.core.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.UserLoginLog;
import com.jbb.server.common.Home;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})

public class UserLoginLogDaoTest {
    @Autowired
    private UserLoginLogDao userLoginLogDao;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testInsertLog() {

        UserLoginLog log = new UserLoginLog(1, "jbbd", 0, "prod-android", null, "127.0.0.1");
        userLoginLogDao.insertUserLoginLog(log);

        log = new UserLoginLog(1, "jbbd", 0, "prod-android", 1, "127.0.0.1");
        userLoginLogDao.insertUserLoginLog(log);
        
        log = new UserLoginLog(1, "jbbd", 1, "prod-android", 1, "127.0.0.1");
        userLoginLogDao.insertUserLoginLog(log);
    }

}
