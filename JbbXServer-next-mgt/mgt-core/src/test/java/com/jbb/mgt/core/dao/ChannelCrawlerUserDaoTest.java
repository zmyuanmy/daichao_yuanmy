package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.ChannelCrawlerUser;
import com.jbb.server.common.Home;
import com.jbb.server.common.util.DateUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class ChannelCrawlerUserDaoTest {

    @Autowired
    private ChannelCrawlerUserDao channelCrawlerUserDao;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    public void testInsert() {
        ChannelCrawlerUser channelCrawlerUser1 = new ChannelCrawlerUser();
        channelCrawlerUser1.setChannelCode("123321");
        channelCrawlerUser1.setCreationDate(DateUtil.getCurrentTimeStamp());
        channelCrawlerUser1.setMerchantId(001);
        channelCrawlerUser1.setPhoneNumber("88888888");
        channelCrawlerUser1.setRegisterDate(DateUtil.getCurrentTimeStamp());
        channelCrawlerUser1.setuId("001");
        channelCrawlerUser1.setUserName("张三");

        ChannelCrawlerUser channelCrawlerUser2 = new ChannelCrawlerUser();
        channelCrawlerUser2.setChannelCode("123321");
        channelCrawlerUser2.setCreationDate(DateUtil.getCurrentTimeStamp());
        channelCrawlerUser2.setMerchantId(001);
        channelCrawlerUser2.setPhoneNumber("88888888");
        channelCrawlerUser2.setRegisterDate(DateUtil.getCurrentTimeStamp());
        channelCrawlerUser2.setuId("001");
        channelCrawlerUser2.setUserName("李四");

        List<ChannelCrawlerUser> channelCrawlerUsers = new ArrayList<ChannelCrawlerUser>();
        channelCrawlerUsers.add(channelCrawlerUser1);
        channelCrawlerUsers.add(channelCrawlerUser2);
        channelCrawlerUserDao.insertChannelsStatisticDaily(channelCrawlerUsers);
    }

    @Test
    public void testSelect() {
        List<ChannelCrawlerUser> channelCrawlerUsers = channelCrawlerUserDao.selectCrawlUsers("123321",
            DateUtil.parseStrnew("2018-08-16 00:00:00"), DateUtil.parseStrnew("2018-08-17 00:00:00"));
        System.out.println(channelCrawlerUsers.size());
    }

    @Test
    public void testCount() {
        int count = channelCrawlerUserDao.countCrawlUsers("123321", DateUtil.parseStrnew("2018-08-16 00:00:00"),
            DateUtil.parseStrnew("2018-08-17 00:00:00"));
        System.out.println("count " + count);
    }

    @Test
    public void testSelectLast() {
        ChannelCrawlerUser channelCrawlerUser = channelCrawlerUserDao.getLastCrawlUser("123321");
    }

    @Test
    public void testExist() {
        boolean flag = channelCrawlerUserDao.selectExistCrawlUser("123321", "001");
    }

}
