package com.jbb.mgt.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.Organization;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserKey;
import com.jbb.mgt.core.domain.UserProperty;
import com.jbb.mgt.server.core.util.PropertiesUtil;
import com.jbb.server.common.Home;
import com.jbb.server.common.exception.WrongUserKeyException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.shared.rs.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})

public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ChannelService channelService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
        PropertiesUtil.init();
    }

    @Test
    @Rollback
    public void UserTest() {

        // 先插入数据
        User user = new User();
        // String code = StringUtil.randomAlphaNum(5);
        user.setUserName("zhang");
        user.setPhoneNumber("222222");
        user.setIdCard("213213213");
        user.setIpAddress("深圳");
        user.setZhimaScore(50);
        user.setQq("12580");
        user.setWechat("wechat");
        user.setChannelCode("100");
        user.setCreationDate(DateUtil.getCurrentTimeStamp());
        userService.insertUser(user);

        // 获取数据并验证
        User user2 = userService.selectUserById(user.getUserId());

        if(null != user2) {
            assertEquals(user.getChannelCode(), user2.getChannelCode());
            assertEquals(user.getUserName(), user2.getUserName());
        }

        // 获取User列表， 返回数据列表
        int orgId = 1;
        List<User> list = userService.selectUsers(orgId, "100", null, null, null, null);
        if (list.size()>0) {
            assertTrue(list.size() == 3);
        }

    }

    @Test
    @Rollback
    public void getUser() {
        User user = userService.selectUser(1, "18675511205");
        assertTrue(user.getUserId() != 0);
    }

    @Test
    public void getUserList() {
        try {
            int orgId = 1;
            List<User> list = userService.selectUsers(orgId, "222444", "1231", "zP6r0", DateUtil.getCurrentTimeStamp(),
                DateUtil.getCurrentTimeStamp());
            assertTrue(list.size() > 0);
        } catch (Exception e) {

        }
    }

    @Test
    public void testUserKey() {

        UserKey userKey = userService.createUserKey(1, 1, 5000000L, true);
        assertNotNull(userKey);

        User user = userService.login(userKey.getUserKey());
        assertNotNull(user);

        userService.logoutUser(userKey.getUserKey());
        try {
            user = userService.login(userKey.getUserKey());
        } catch (WrongUserKeyException ex) {
            assertEquals(ex.getApiErrorCode(), 2);
        }
    }

    @Test
    public void testGetUserDatil() {
        List<User> list = userService.selectUserDetails("011", DateUtil.getCurrentTimeStamp(),
            DateUtil.getCurrentTimeStamp(), 1, false, 1);
        assertTrue(list.size() > 0);
    }

    @Test
    public void testSelectUserApplyDetail() {
        List<User> users = userService.selectUserApplyDetails("jbbd", null, null, null, null, false, false, false);
        assertTrue(users.size() > 0);
    }

    @Test
    public void testSelectUserApplyDetailList() {
        List<Organization> users = userService.selectUserApplyDetailsList("10010044", null, null);
        assertTrue(users.size() > 0);
    }

    @Test
    public void testGetTotal() {
        Timestamp startDate = Util.parseTimestamp("2018-08-20", TimeZone.getDefault());
        Timestamp endDate = Util.parseTimestamp("2018-08-21", TimeZone.getDefault());
        long total = userService.selectUserDetailsTotal(null, startDate, endDate, 188, true, 2);

        System.out.println("============" + total);
    }

    @Test
    public void testHideUserLogin() {
        Channel channel = channelService.getChannelByCode("jbbd");
        List<UserProperty> userProperties = new ArrayList<UserProperty>();
        int cnt1 = 0, cnt2 = 0;
        for (int i = 0; i < 100; i++) {
            boolean flag = userService.needHiddenUserForChannel(channel, "18575511201", "127.0.0.1", userProperties);
            if (flag) {
                cnt1++;
            } else {
                cnt2++;
            }
            System.out.println("============" + flag);
        }
        System.out.println("============" + cnt1);
        System.out.println("============" + cnt2);
    }

    @Test
    public void test2() {
        User user = userService.selectUserByApplyId(10004, 1);
        System.out.println("============" + user);
    }

}
