package com.jbb.mgt.core.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jbb.mgt.core.domain.JointUserLoginData;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.mgt.core.domain.User;
import com.jbb.server.common.Home;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})

public class AccountLoginLogServiceTest {

    @Autowired
    private AccountLoginLogService accountLoginLogService;

    @Autowired
    private UserService userService;

    @Autowired
    private XiaoCaiMiService xiaoCaiMiService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    @Test
    @Rollback
    public void UserLoginLogTest() {

        // 插入一个用户
        User user = new User();
        user.setIdCard("test");
        user.setUserName("test");
        user.setWechat("wechat");
        userService.insertUser(user);

        // 先插入数据
        Date nowdate = new Date();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp loginDate = Timestamp.valueOf(simpleDate.format(nowdate));
        accountLoginLogService.insertAccountLoginLog(user.getUserId(), "shenzheng", loginDate);

        // // 获取数据并验证
        // UserLoginLog userLoginLog=new UserLoginLog();
        //
        // assertEquals(userLoginLog.getUserId(), user.getUserId());
        // assertEquals(userLoginLog.getIpAddress(), "shenzheng");
        // assertEquals(userLoginLog.getLoginDate(), new Timestamp(0));

    }

    @Test
    public void test() {
        JointUserLoginData data = null;
        try {
            data = xiaoCaiMiService.jointUserLogin(
                "SMwoi7Br+9tVVvu0MbFVL7PJu6dxS+TXbttwr61aWO6QaWm6FF6bTfuKpQGC+pEmX8xk3VRHXgnB0ucHDJzTrwHxr0+H+qHMoiERzPAk9EDtn5x8+cfd7yEIxRcdQDa/yxuW/YQl0zmemKuyr5QvJM7dNKm8RioygA8zsC4x8YqOu19QXgE4D0LP53/jg1pS8e2SkJzkmp3+MUr3g9PP37b4YjCt6fy3g0F0cZHPwrvSGNHKFSQHHahvnhqgYiXM/qhsVDL3Cp1QX3VXqRkQ5SARyDWyqN2PSis9dwwL8sDuIhzuCEhXjsULJpO6eKAL5xwpz009cw2pQykFY6WUKQ==",
                "bMBuHwp84llyMhjdO77K1ATOtKdVhgSpoX2jz83gS8I=");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("data=====" + data);
    }
}
