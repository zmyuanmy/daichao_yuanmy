package com.jbb.mgt.core.dao;

import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.server.common.Home;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})

public class MessageCodeDaoTest {

    @Autowired
    private MessageCodeDao messageCodeDao;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testMessageCode() {
        String phoneNumber = "18575511205";
        String msgCode = StringUtil.randomAlphaNum(4);
        String channelCode = "testChannel";
        Timestamp ts = DateUtil.getCurrentTimeStamp();
        Timestamp expireDate = new Timestamp(ts.getTime() + 6000000L);
        messageCodeDao.saveMessageCode(phoneNumber, channelCode, msgCode, ts, expireDate);

        boolean flag = messageCodeDao.checkMsgCode(phoneNumber, channelCode, msgCode, DateUtil.getCurrentTimeStamp());

        assertTrue(flag);

    }
}
